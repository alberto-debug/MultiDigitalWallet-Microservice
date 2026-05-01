# MultiDigitalWallet Microservices Architecture

## Overview
Multi-service microservices architecture using Spring Boot and Docker Compose for local development.

## Directory Structure
```
multidigitalwallet/
├── services/              # Microservices
│   ├── user-service/      # User management service
│   ├── payment-service/   # (future) Payment processing
│   └── wallet-service/    # (future) Wallet management
├── shared/                # Shared libraries, DTOs, common code
├── docs/                  # Architecture documentation
├── docker-compose.yml     # Local development orchestration
└── .env                   # Environment variables
```

## Services

### User Service
- **Port:** 8081
- **Database:** PostgreSQL (port 5432)
- **Responsibilities:** User authentication, JWT token management, admin functions
- **Environment Variables:**
  - `JWT_SECRET`: Secret key for JWT signing
  - `JWT_EXPIRATION_MS`: Token expiration time (milliseconds)
  - `JWT_ISSUER`: JWT issuer identifier
  - `ADM_EMAIL`: Admin email
  - `ADM_PASSWORD`: Admin password

## Running Locally

```bash
# Start all services
docker compose up

# Stop all services
docker compose down

# View logs
docker compose logs -f user-service
```

## Future Services
- **Payment Service:** Payment processing and transaction management
- **Wallet Service:** Digital wallet management and balance tracking

## Configuration
All services are configured via `.env` file. Each service reads its environment variables from the Docker Compose configuration.

## Health Checks
- PostgreSQL: TCP ping on port 5432
- User Service: HTTP GET /health endpoint on port 8081
