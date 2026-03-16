# Document Flow System - Backend API

The core REST API for the ECM system, responsible for data persistence, business logic, and document workflow management.

## Tech Stack
* **Java 21**
* **Spring Boot 3.x** (Web, Data JPA)
* **PostgreSQL** (via Docker)
* **Lombok**
* **Maven**

## Local Setup

1. Start the database instance:
```bash
docker compose up -d
```

2. Run the application:
```bash
./mvnw spring-boot:run
```
The server listens on port 8080 by default. Cross-Origin Resource Sharing (CORS) is configured to accept requests from the local Angular dev server (http://localhost:4200)
