# MultiDigitalWallet

<img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" /> <img src="https://img.shields.io/badge/Spring%20Boot-4.0.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot" /> <img src="https://img.shields.io/badge/Spring%20Security-2F4F4F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security" /> <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" /> <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT" />

## Overview

MultiDigitalWallet is being developed as a microservices-based digital wallet platform. This repository acts as the platform entry point: it documents the overall architecture, Docker orchestration, and the service layout while each service keeps its own implementation details in a local README.

## Table of Contents
- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Microservices with Docker](#microservices-with-docker)
- [Repository Docs](#repository-docs)
- [Platform Notes](#platform-notes)
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

## Microservices with Docker

This platform is designed around independently deployable services that communicate through explicit APIs instead of shared internals.

### Docker strategy

- **One service per container**: each microservice runs in its own isolated runtime.
- **Infrastructure as code**: Docker Compose defines local dependencies and makes the stack reproducible.
- **Loose coupling**: services should communicate through APIs, not shared application internals.
- **Scalability path**: the architecture can grow service-by-service without turning into a monolith.

### Local orchestration

- `docker-compose.yml` at the repository root can be used to bring up the platform stack.
- The service module also includes a local `compose.yaml` for service-level infrastructure.
- In a full microservices setup, this same pattern can be extended with additional services such as authentication, wallet, transactions, and notifications.

## Repository Docs

- [`user-service` README](user-service%20/user-service%20/README.md): service-specific setup, configuration, and runtime notes
- [`docker-compose.yml`](docker-compose.yml): repository-level orchestration for local development

## Platform Notes

- The root README stays intentionally high level.
- Service-specific environment variables, run commands, and implementation details belong in the service README.
- As new services are added, create a README in each module and link it from the root.

## Testing

Run the relevant service tests from the module README. The root repository currently serves as the platform entry point rather than a build target.

If you are working inside a service module, follow that module’s own test commands and environment setup.

