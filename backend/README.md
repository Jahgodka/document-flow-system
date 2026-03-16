# Document Flow System (Backend)

A Proof of Concept (PoC) for an Enterprise Content Management (ECM) system, designed to handle document workflows using a REST API architecture.

## Tech Stack
* **Java 21**
* **Spring Boot 3.x** (Spring Web, Spring Data JPA)
* **PostgreSQL** (Docker)
* **Lombok**
* **Maven**

## Requirements
- **Docker**
- **Java 21**

## Local Setup

1. Start the PostgreSQL database instance using Docker:

   ```bash
   docker compose up -d
   ```

2. Run the application using the integrated Maven Wrapper:

    ```bash
    ./mvnw spring-boot:run
    ```

    The application will start on:

    ```
    http://localhost:8080
    ```

    ---

## API Endpoints

Example endpoints available in the application:

    ```http
    GET /documents
    ```

Returns a list of documents.

    ```http
    POST /documents
    ```

Creates a new document.
