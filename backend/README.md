# Document Flow System - Backend API

The core REST API for the ECM system, responsible for data persistence, business logic, and AI-driven document workflow management.

## Tech Stack
* **Java 21**
* **Spring Boot 3.x** (Web, Data JPA)
* **PostgreSQL** (via Docker)
* **Google Gemini API** (RestClient & Jackson ObjectMapper integration)
* **Lombok**
* **Maven**

## AI Classification Logic & Fault Tolerance
The system intercepts incoming POST requests and dynamically builds a prompt based on the document's title and content. It then queries the Gemini API to determine the document's status. A Fallback mechanism is implemented.

## Local Setup

### 1. Start the database instance:
```bash
docker compose up -d
```

### 2.Environment Variable:
The system uses environment variables to authorize Google services securely. Before running the project, you must set the following variable in your OS or IDE configuration: GEMINI_API_KEY=your_actual_api_key_here

### 3. Run the application:
```bash
./mvnw spring-boot:run
```
The server listens on port 8080 by default. Cross-Origin Resource Sharing (CORS) is configured to accept requests from the local Angular dev server (http://localhost:4200)
