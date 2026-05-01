# MultiDigitalWallet - User Service

<img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" /> <img src="https://img.shields.io/badge/Spring%20Boot-4.0.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot" /> <img src="https://img.shields.io/badge/Spring%20Security-2F4F4F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security" /> <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" /> <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT" />

## Overview

This module contains the backend user service for **MultiDigitalWallet**. It focuses on authentication, authorization, user and role persistence, JWT token handling, and bootstrap data for the platform.

The HTTP API is still evolving, so this README focuses on the module runtime, security foundation, and local development flow.

## Table of Contents
- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Configuration](#configuration)
- [Microservices with Docker](#microservices-with-docker)
- [Run Locally](#run-locally)
- [Security Notes](#security-notes)
- [Testing](#testing)

## Tech Stack

- Java 21
- Spring Boot 4.0.5
- Spring Security
- Spring Data JPA
- PostgreSQL
- Auth0 Java JWT
- Lombok
- Testcontainers for integration tests

## Configuration

Create the required environment variables before running the module:

```env
DB_URL=jdbc:postgresql://localhost:5432/userdb
DB_USERNAME=postgres
DB_PASSWORD=postgres_password

JWT_SECRET=your_secret_key_here
JWT_EXPIRATION_MS=3600000
JWT_ISSUER=user-service

ADM_EMAIL=admin@example.com
ADM_PASSWORD=admin_password
```

The module reads these values from `src/main/resources/application.properties`.

## Microservices with Docker

This service is designed to run as an independent container inside the MultiDigitalWallet platform.

### Docker strategy

- **One service per container**: the user-service should be deployed independently from other modules.
- **Infrastructure as code**: Docker Compose keeps local development reproducible.
- **Service ownership**: user identity, roles, and auth logic stay inside this module.

### Local orchestration

- `docker-compose.yml` at the repository root can start the platform stack.
- `compose.yaml` inside this module starts the local PostgreSQL dependency.
- When new services are added, they should follow the same pattern: module-level README plus containerized runtime.

## Run Locally

From `user-service /user-service`:

```bash
./mvnw clean test
./mvnw spring-boot:run
```

If you want to start only the local PostgreSQL dependency:

```bash
docker compose -f compose.yaml up -d
```

## Security Notes

- `SecurityConfig` keeps the service stateless and enables CORS for local development.
- `SecurityFilter` validates Bearer tokens before requests reach the controller layer.
- `TokenService` signs and validates JWTs using the configured secret and issuer.
- `RoleSeeder` creates `ROLE_USER` and `ROLE_ADMIN` if they do not already exist.
- `AdminSeeder` creates the default admin user only when it is missing.

## Testing

Run the test suite with:

```bash
./mvnw test
```

For a clean verification run:

```bash
./mvnw clean test
```

