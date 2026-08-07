# 🗳️ Election Analysis REST API

A Spring Boot REST API that analyzes election data from CSV files and exposes the results through REST endpoints.

## Features

* Read election data from CSV files
* Analyze party-wise election results
* Generate state-wise election summaries
* Generate constituency-wise election summaries
* Find winners with an absolute majority (more than 50% vote share)
* Interactive API documentation using Swagger UI

## Technologies Used

* Java
* Spring Boot
* Maven
* Apache Commons CSV
* Java Streams API
* Lombok
* Swagger (OpenAPI)

## Project Structure

```text
src
├── controller
├── service
├── model
├── dto
├── util
├── exception
└── resources
```

## API Endpoints

* `GET /api/elections/results`
* `GET /api/elections/candidates`
* `GET /api/elections/party-summary`
* `GET /api/elections/state-summary`
* `GET /api/elections/constituency-summary`
* `GET /api/elections/majority-winners`

## Swagger UI

After running the application, open:

```
http://localhost:8080/swagger-ui/index.html
```

## How to Run

1. Clone the repository.
2. Open the project in your preferred IDE.
3. Build the project using Maven.
4. Run the Spring Boot application.
5. Test the APIs using Swagger UI or Postman.

## Purpose

This project demonstrates how to build a Spring Boot REST API that processes election data from CSV files using Java Streams and exposes analytical reports through REST endpoints.

---

**Developer:** Aman Kumar
