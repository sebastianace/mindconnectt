# Changelog

## [Sin publicar] - feature/spring-security

### Agregado
- Autenticación y autorización con Spring Security y JWT (API stateless).
- Contextos `role` y `user` (domain, application, infrastructure) y migraciones V53-V56 (`roles`, `users`, `user_roles` y roles base).
- Login en `POST /api/auth/login`, registro público en `POST /api/users/register` y administración de usuarios solo para `ROLE_ADMIN`.
- `JwtAuthenticationFilter` (login) y `JwtValidationFilter` (validación del token en cada petición).
- Respuestas 401 y 403 en formato `application/problem+json`.
- Creación del primer administrador desde variables de entorno (`ADMIN_USERNAME`, `ADMIN_PASSWORD`).
- Pruebas de dominio, aplicación y del manejo de JWT.

### Seguridad
- La clave JWT se lee de `JWT_SECRET` (nunca se escribe en el código) y la aplicación no arranca si falta o es débil.
- El registro público no permite autoasignarse `ROLE_ADMIN`.

## [1.0.0] - 2026-10-05

### Agregado
- Esquema completo de 52 tablas con migraciones Flyway V1-V52 para MySQL 8.
- 52 bounded contexts con capas domain, application e infrastructure (DDD + hexagonal).
- CRUD REST para los 52 recursos con validación de entrada (Jakarta Validation).
- Validación de las 70 llaves foráneas en los casos de uso (HTTP 422).
- Validación de códigos duplicados en catálogos (HTTP 409).
- Invariantes de negocio en los agregados y restricciones CHECK en la base de datos.
- Publicación de eventos de dominio mediante el puerto DomainEventPublisher.
- Transacciones en los casos de uso mediante AOP, sin acoplar la capa application a Spring.
- Manejo global de errores con ProblemDetail (RFC 9457).
- Configuración de CORS por variable de entorno.
- 178 pruebas unitarias (68 de dominio y 110 de aplicación).
