# Distributed Rate Limiter

## Problem
Uncontrolled incoming requests can easily overwhelm a service, bringing down the database or crashing servers via OOM. Malicious users or buggy scripts can send thousands of requests per second.

## Architecture
This project implements a distributed **Token Bucket algorithm** using Redis Lua Scripts. 
A Lua script ensures that reading the current token count and decrementing it happens atomically in a single network roundtrip. This prevents race conditions in distributed microservice architectures where multiple instances of the Rate Limiter might check Redis simultaneously.

A Spring `OncePerRequestFilter` intercepts incoming traffic, evaluates the IP address against the Redis token bucket, and returns `429 Too Many Requests` if the bucket is empty.

## Tech Stack
* **Language:** Java 21
* **Framework:** Spring Boot 3, Spring Web
* **Database/Cache:** Redis (Spring Data Redis)
* **Testing:** JUnit 5, Testcontainers (Spins up a real Redis instance in Docker for integration testing)
* **Deployment:** Docker, Docker Compose

## API Endpoints
* `GET /api/v1/free` - Open access. No limits applied.
* `GET /api/v1/limited` - Rate limited to 10 requests per second per IP (configurable).

## How To Run Locally
1. Start Redis:
   ```bash
   docker-compose up -d
   ```
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Automated Testing
This repository includes an automated CI/CD pipeline via GitHub Actions.
You can run the integration tests locally using:
```bash
mvn test
```
*Note: Docker must be running on your machine for Testcontainers to spin up the isolated Redis database.*
