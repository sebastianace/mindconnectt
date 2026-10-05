# Changelog

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
