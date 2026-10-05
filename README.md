# MindConnect · Migraciones con Flyway + DDD + Arquitectura Hexagonal

API REST de MindConnect (plataforma de salud mental) construida con **Java 21**, **Spring Boot 4**, **Spring Data JPA**, **Flyway** y **MySQL 8**, organizada con **Domain-Driven Design** y **arquitectura hexagonal** (puertos y adaptadores).

El esquema tiene **52 tablas**. Cada tabla es un *bounded context* con sus tres capas (`domain`, `application`, `infrastructure`) dentro de una sola aplicación Spring Boot y una sola base de datos (monolito modular, no microservicios).

| Métrica | Valor |
| --- | --- |
| Tablas / migraciones Flyway | 52 / 52 |
| Bounded contexts | 52 |
| Llaves foráneas validadas en casos de uso | 70 |
| Pruebas unitarias | 178 (68 dominio + 110 aplicación) |
| Archivos Java | 1.375 |

---

## 1. Arquitectura

```text
mindconnect/
├── pom.xml            # Padre Maven (packaging pom) con el BOM de Spring Boot
├── domain/            # Núcleo: agregados, value objects, eventos, puertos. Java puro.
├── application/       # Casos de uso, commands, DTOs, excepciones. Depende solo de domain.
├── infrastructure/    # REST, JPA, Flyway, configuración y clase principal.
├── .env.example       # Plantilla de variables de entorno (sin secretos)
└── mvnw / mvnw.cmd    # Maven Wrapper
```

Dirección de dependencias (la regla de la arquitectura hexagonal):

```text
infrastructure ──► application ──► domain
```

- `domain` **no depende de nada**: ni Spring, ni JPA, ni Jakarta Validation.
- `application` **solo depende de `domain`**: los casos de uso son clases Java normales, sin `@Service` ni `@Transactional`.
- `infrastructure` implementa los puertos (adaptadores) y conecta todo con Spring.

Recorrido de una petición:

```text
Controller ─► UseCase ─► Repository (puerto) ─► RepositoryAdapter ─► JpaRepository ─► MySQL
                 │
                 └─► DomainEventPublisher (puerto) ─► SpringDomainEventPublisher ─► listeners
```

### Estructura de cada bounded context (ejemplo: `country`)

```text
domain/.../country/
├── event/                      CountryRegisteredEvent, CountryUpdatedEvent, CountryDeletedEvent
├── model/aggregate/            Country (agregado raíz con sus invariantes)
├── model/valueobject/          CountryId (identidad tipada, UUID)
└── port/repository/            CountryRepository (puerto de salida)

application/.../country/
├── command/                    RegisterCountryCommand, UpdateCountryCommand
├── dto/                        CountryResponse (con fábrica CountryResponse.from(agregado))
├── exception/                  CountryNotFoundApplicationException
└── usecase/                    Register, GetById, List, Update, Delete

infrastructure/.../country/
├── adapters/in/rest/controllers/       CountryController
├── adapters/in/rest/dtos/              CreateCountryRequest, UpdateCountryRequest
├── adapters/out/persistence/entity/    CountryJpaEntity
├── adapters/out/persistence/mappers/   CountryPersistenceMapper
├── adapters/out/persistence/repositories/  CountryJpaRepository, CountryRepositoryAdapter
└── config/                             CountryBeansConfig (ensamblado explícito de beans)
```

### Piezas compartidas (`common`)

| Capa | Clase | Responsabilidad |
| --- | --- | --- |
| domain | `AggregateRoot` | Registro de eventos de dominio en cada agregado |
| domain | `DomainEvent` | Contrato de todos los eventos |
| domain | `DomainEventPublisher` | **Puerto** para publicar eventos |
| domain | `DomainGuard` | Validaciones reutilizables de invariantes (texto, email, números) |
| domain | `DomainValidationException` | Violación de una regla de negocio |
| application | `ApplicationException` y derivadas | `NotFound…`, `ReferenceNotFound…`, `DuplicateResource…` |
| infrastructure | `GlobalExceptionHandler` | Traduce excepciones a HTTP con `ProblemDetail` (RFC 9457) |
| infrastructure | `UseCaseTransactionConfig` | Transacciones en los casos de uso sin anotar la capa application |
| infrastructure | `SpringDomainEventPublisher` | **Adaptador** del puerto de eventos |
| infrastructure | `DomainEventLogListener` | Registra cada evento después del commit |
| infrastructure | `CorsConfig` | Aplica `CORS_ALLOWED_ORIGINS` a `/api/**` |

---

## 2. Decisiones de diseño (DDD)

**Referencias entre agregados por identidad.** Las llaves foráneas se modelan como value objects (`CountryId`, `PatientId`…) y no como `@ManyToOne`. Es la recomendación de DDD: cada agregado es una frontera de consistencia y no carga grafos de otros agregados. La integridad referencial se garantiza en dos niveles:

1. **Aplicación:** antes de registrar o actualizar, el caso de uso verifica con `existsById` que cada referencia exista (70 llaves foráneas en total) y responde **422** con un mensaje claro.
2. **Base de datos:** las `FOREIGN KEY` de las migraciones siguen siendo la última defensa.

**Invariantes dentro del agregado.** El agregado no permite quedar en un estado inválido:

| Agregado | Regla |
| --- | --- |
| Todos | Los textos obligatorios no pueden ser nulos ni estar en blanco |
| `Patient`, `Contact`, `EmailContact` | El email debe tener un formato válido |
| `Patient` | La fecha de nacimiento no puede estar en el futuro |
| `TreatmentPlan` | `endDate` no puede ser anterior a `startDate` |
| `Encounter` | `endedAt` no puede ser anterior a `startedAt` |
| `AiModel` | Precios ≥ 0, `maxTokens` > 0, `contextWindow` > 0 y `maxTokens` ≤ `contextWindow` |
| `ChatAiRunMetric` | Tokens ≥ 0, `totalTokens = promptTokens + completionTokens`, `cost` ≥ 0 |

Las reglas más importantes también existen como `CHECK` en MySQL.

**Eventos de dominio publicados de verdad.** `register(...)` y `update(...)` registran un evento en el agregado; el caso de uso lo publica por el puerto `DomainEventPublisher` y luego limpia la lista. `Delete` publica su `DeletedEvent`. En infraestructura, `DomainEventLogListener` los escucha con `@TransactionalEventListener`, así que solo se registran si la transacción se confirmó.

**Transacciones sin contaminar la capa de aplicación.** `UseCaseTransactionConfig` aplica, mediante AOP, una transacción al método `execute(...)` de todas las clases `*UseCase`:

- `Register`, `Update`, `Delete`: transacción de escritura, con rollback ante cualquier `RuntimeException`.
- `Get`, `List`: transacción de solo lectura.

Así, en `Update`, el `findById`, la validación y el `save` ocurren en una única transacción, y `application` sigue sin depender de Spring.

**Ensamblado explícito.** Ningún caso de uso lleva `@Service`; cada `*BeansConfig` crea sus beans con `new`, lo que deja visible en un solo lugar de qué depende cada caso de uso.

---

## 3. Migraciones (Flyway)

Ubicación: `infrastructure/src/main/resources/db/migration/`, de `V1` a `V52`, ordenadas por dependencias:

- **Geografía y catálogos:** `countries`, `state_regions`, `city_municipalities`, `document_types`, `genders`, `relationship_types`, `professional_types`, `studies`.
- **Personas y contactos:** `professionals`, `patients`, `contacts`, `phone_contacts`, `email_contacts`, `patient_contacts`, `patient_allergies`, `professional_studies`.
- **Información clínica:** `clinical_record_statuses`, `clinical_records`, `encounter_types`, `encounter_modalities`, `encounter_statuses`, `encounters`, `risk_levels`, `risk_assessments`, `clinical_notes`, `mental_status_exams`, `treatment_statuses`, `treatment_plans`, `treatment_goal_statuses`, `treatment_goals`, `medication_routes`, `assessment_types`, `consent_types`, `diagnostic_systems`.
- **Chat:** `conversations_statuses`, `priorities`, `sender_types`, `message_types`, `chat_conversations`, `chat_participants`, `chat_messages`.
- **IA:** `provider_models_ai`, `ai_models`, `chat_conversation_ai_settings`, `ai_runs_statuses`, `chat_ai_runs`, `chat_ai_run_metrics`, `chat_ai_run_errors`.
- **Escalaciones:** `escalations_statuses`, `chat_escalations`, `chat_escalation_assignments`, `chat_escalation_status_history`.

Restricciones destacadas:

| Restricción | Motivo |
| --- | --- |
| `uk_countries_code_country` | El código de país es único |
| `uk_state_regions_country_code` (`country_id`, `code_region`) | El código de región es único **dentro de su país** |
| `uk_city_municipalities_region_code` (`region_id`, `code_city`) | El código de ciudad es único **dentro de su región** |
| `uk_professionals_document` / `uk_patients_document` (`document_type_id`, `document_number`) | Un documento es único por tipo (CC 123 y TI 123 pueden coexistir) |
| `ck_treatment_plans_dates`, `ck_encounters_dates` | Coherencia de fechas |
| `ck_ai_models_prices`, `ck_ai_models_tokens` | Precios y límites de tokens válidos |
| `ck_chat_ai_run_metrics_tokens`, `ck_chat_ai_run_metrics_cost` | Métricas coherentes |

Hibernate trabaja con `ddl-auto: validate`: **Flyway es el único dueño del esquema** e Hibernate solo verifica que las entidades coincidan con las tablas.

> Regla de oro: no se editan migraciones ya aplicadas en una base con datos. Cualquier cambio futuro va en una nueva `V53__...sql`.

---

## 4. API REST

Los 52 recursos siguen el mismo contrato:

| Método | Ruta | Respuesta exitosa |
| --- | --- | --- |
| `POST` | `/api/{recurso}` | `201 Created` |
| `GET` | `/api/{recurso}` | `200 OK` |
| `GET` | `/api/{recurso}/{uuid}` | `200 OK` |
| `PUT` | `/api/{recurso}/{uuid}` | `200 OK` |
| `DELETE` | `/api/{recurso}/{uuid}` | `204 No Content` |

Los cuerpos exactos están en los records `Create*Request` y `Update*Request` de cada contexto.

### Manejo de errores

Todas las respuestas de error usan el formato estándar `application/problem+json`:

| Situación | HTTP |
| --- | --- |
| Campos inválidos (`@NotBlank`, `@Size`, `@Email`, `@PastOrPresent`, `@PositiveOrZero`…) | `400` con el detalle por campo |
| JSON mal formado o UUID inválido en la ruta | `400` |
| Regla de negocio violada en el agregado | `400` |
| El recurso del `{uuid}` no existe | `404` |
| Código o valor único duplicado | `409` |
| Eliminar un registro que otros referencian | `409` |
| Una llave foránea del cuerpo apunta a un registro inexistente | `422` |

Ejemplo, registrar una región con un país que no existe:

```json
{
  "type": "about:blank",
  "title": "Referenced resource not found",
  "status": 422,
  "detail": "Referenced Country not found with id: 7d1c2f0e-3b7a-4c55-9d11-2f6a8f0c1e22",
  "instance": "/api/state-regions"
}
```

Ejemplo, campos inválidos:

```json
{
  "title": "Invalid request",
  "status": 400,
  "detail": "One or more fields are invalid",
  "errors": {
    "nameCountry": "nameCountry is required",
    "telephonePrefix": "telephonePrefix must have at most 5 characters"
  }
}
```

Los mensajes SQL nunca se envían al cliente; solo quedan en el log del servidor.

---

## 5. Instalación y ejecución

### Requisitos

- **JDK 21** completo (con `javac`), por ejemplo Eclipse Temurin 21. Verifica con `java -version` y `javac -version`.
- **MySQL 8** (local o en Docker).
- Git. Opcional: VS Code con *Extension Pack for Java* y *Spring Boot Extension Pack*.

No necesitas instalar Maven: el proyecto trae Maven Wrapper.

### Paso 1: crear la base de datos

```sql
CREATE DATABASE mindconnect CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'mindconnect_user'@'localhost' IDENTIFIED BY 'TU_CLAVE';
GRANT ALL PRIVILEGES ON mindconnect.* TO 'mindconnect_user'@'localhost';
FLUSH PRIVILEGES;
```

O con Docker:

```bash
docker run --name mindconnect-mysql \
  -e MYSQL_DATABASE=mindconnect -e MYSQL_USER=mindconnect_user \
  -e MYSQL_PASSWORD=TU_CLAVE -e MYSQL_ROOT_PASSWORD=OTRA_CLAVE \
  -p 3306:3306 -d mysql:8.0
```

Flyway crea las tablas, pero **la base debe existir** antes de arrancar.

### Paso 2: configurar `.env`

Desde la raíz del proyecto copia la plantilla (`cp .env.example .env` o `Copy-Item .env.example .env` en PowerShell) y ajusta los valores:

```properties
DB_URL=jdbc:mysql://localhost:3306/mindconnect
DB_USERNAME=mindconnect_user
DB_PASSWORD=TU_CLAVE
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8081
CORS_ALLOWED_ORIGINS=http://localhost:5173,http://localhost:3000
QUEUE_BLOCK_USERS_FIXED_DELAY_MS=5000
```

`.env` está en `.gitignore`: nunca lo subas al repositorio.

### Paso 3: compilar y ejecutar las pruebas

```bash
./mvnw clean verify        # Linux / macOS
.\mvnw.cmd clean verify    # Windows
```

Debe terminar en `BUILD SUCCESS`. Las pruebas no necesitan MySQL.

### Paso 4: arrancar

Con MySQL encendido y **desde la raíz del proyecto** (para que Spring encuentre `.env`):

```bash
java -jar infrastructure/target/infrastructure-1.0-SNAPSHOT.jar
```

Al arrancar: Spring lee `.env` → Flyway aplica `V1`…`V52` → Hibernate valida las entidades → la API queda en `http://localhost:8081`.

Desde VS Code: abre la carpeta raíz, ejecuta `Maven: Reload Projects` y lanza la configuración **Spring Boot-MindConnectApplication&lt;infrastructure&gt;** con `F5`.

### Paso 5: probar el flujo completo

```bash
# 1. Crear un país (201)
curl -s -X POST http://localhost:8081/api/countries -H "Content-Type: application/json" \
  -d '{"nameCountry":"Colombia","codeCountry":"CO","description":"Colombia","active":true,"telephonePrefix":"+57"}'

# 2. Repetir el mismo código (409 Duplicate resource)
curl -s -X POST http://localhost:8081/api/countries -H "Content-Type: application/json" \
  -d '{"nameCountry":"Otro","codeCountry":"CO","description":"Duplicado","active":true,"telephonePrefix":"+57"}'

# 3. Consultar un UUID que no existe (404)
curl -s http://localhost:8081/api/countries/00000000-0000-0000-0000-000000000000

# 4. Enviar un nombre vacío (400 con detalle por campo)
curl -s -X POST http://localhost:8081/api/countries -H "Content-Type: application/json" \
  -d '{"nameCountry":"","codeCountry":"PE","description":"Peru","active":true,"telephonePrefix":"+51"}'
```

Para verificar las migraciones aplicadas:

```sql
SELECT installed_rank, version, description, success
FROM flyway_schema_history ORDER BY installed_rank;
```

---

## 6. Pruebas

| Módulo | Qué se prueba |
| --- | --- |
| `domain` | Creación de los 52 agregados con su evento; `DomainGuard`; invariantes de `Patient`, `TreatmentPlan` y `ChatAiRunMetric` |
| `application` | `Delete` de los 52 contextos (incluye publicación del evento); registro de `StateRegion` con referencia inexistente (422) y código duplicado (409); actualización de `Country` con evento, 404 y regla de dominio |

Las pruebas usan repositorios en memoria que implementan los puertos del dominio: no necesitan Spring ni base de datos, lo que demuestra que el núcleo es independiente de la infraestructura.

---

## 7. Solución de problemas

| Error | Causa y solución |
| --- | --- |
| `Could not resolve placeholder 'DB_URL'` | Falta `.env` o se arrancó fuera de la raíz del proyecto |
| `Unknown database 'mindconnect'` | Crea la base (paso 1) |
| `Access denied for user` | Revisa usuario, contraseña y permisos sobre `mindconnect.*` |
| Puerto 3306 u 8081 ocupado | Cambia el puerto en Docker (`-p 3307:3306`) o `SERVER_PORT` en `.env` |
| Flyway: *checksum mismatch* | Se editó una migración ya aplicada. Usa una base nueva de desarrollo; no ejecutes `clean` sobre datos reales |
| Hibernate: falta una tabla o columna | Flyway no terminó o se apunta a otra base. Revisa el log y `flyway_schema_history` |
| `JAVA_HOME` incorrecto | `java -version`, `javac -version` y `mvnw -version` deben mostrar Java 21 |

---

## 8. Alcance

El proyecto implementa el esquema completo con Flyway y un CRUD con reglas de negocio para los 52 contextos. No incluye autenticación, lógica clínica ni integración real con modelos de IA, porque no forman parte del alcance de la actividad.
