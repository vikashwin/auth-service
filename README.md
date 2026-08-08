# Auth Service

Authentication and authorization microservice for the Ecommerce Platform.

## Technology Stack

- Java 21
- Spring Boot 4.1.0
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- Spring Cloud Eureka
- Spring Cloud OpenFeign
- Springdoc OpenAPI
- Docker
- GitHub Actions

## Architecture

```text
                    ┌──────────────────────┐
                    │   Eureka Server      │
                    │      :8761           │
                    └──────────┬───────────┘
                               │
                               │ Service Discovery
                               │
                    ┌──────────▼───────────┐
                    │     Auth Service     │
                    │        :8081         │
                    └──────────┬───────────┘
                               │
                               │ JDBC
                               │
                    ┌──────────▼───────────┐
                    │     PostgreSQL       │
                    │        :5432         │
                    └──────────────────────┘