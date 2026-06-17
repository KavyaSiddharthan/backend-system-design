<div align="center">
  <h1>Backend System Design & Microservices Architecture</h1>
  <p>
    <b>A production-ready collection of highly scalable, distributed system patterns implemented in Java and Spring Boot.</b>
  </p>
  <p>
    <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java" alt="Java 21" />
    <img src="https://img.shields.io/badge/Spring_Boot-3.2-brightgreen?style=for-the-badge&logo=spring" alt="Spring Boot 3" />
    <img src="https://img.shields.io/badge/Redis-Redisson-red?style=for-the-badge&logo=redis" alt="Redis" />
    <img src="https://img.shields.io/badge/PostgreSQL-15-blue?style=for-the-badge&logo=postgresql" alt="PostgreSQL" />
    <img src="https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker" alt="Docker" />
  </p>
</div>

---

## Overview

This repository demonstrates practical, enterprise-grade implementations of complex backend architecture patterns. Instead of abstract diagrams, this repository contains **fully functional, containerized microservices** that solve real-world scalability and concurrency challenges.

Each module is an independent Spring Boot application designed to handle edge cases like race conditions, thread starvation, and network latency in distributed systems.

---

## Architecture Modules

| System / Pattern | Tech Stack | Description |
| :--- | :--- | :--- |
| **[API Gateway & Circuit Breaker](./api-gateway)** | `Spring Cloud Gateway`, `Resilience4j` | Centralized routing with dynamic circuit breaking and fallback controllers to handle downstream service failures gracefully. |
| **[Distributed Rate Limiter](./rate-limiter)** | `Redis`, `Lua Scripts`, `Testcontainers` | High-concurrency Token Bucket algorithm using atomic Redis Lua scripts to prevent API abuse and OOM crashes. |
| **[Distributed Lock](./distributed-lock)** | `Redisson`, `Redis` | Prevents race conditions and overselling in clustered environments by acquiring atomic database locks via Redisson. |
| **[Distributed Job Scheduler](./job-scheduler)** | `Quartz`, `PostgreSQL` | Cluster-safe cron job execution utilizing PostgreSQL JDBC row-locking to ensure jobs run exactly once across instances. |
| **[Async File Processing](./file-processing-system)** | `AWS S3 (LocalStack)`, `@Async` | Non-blocking file upload service that streams multipart data to S3 using background thread pools. |
| **[URL Shortener](./url-shortener)** | `Base62`, `PostgreSQL`, `Redis Caching` | Core hashing algorithms, collision handling, and `@Cacheable` integration for high-read throughput. |
| **[Dynamic Feature Flags](./feature-flags)** | `Redis`, `Spring Data` | Allows instant <1ms boolean toggling of code paths (e.g., checkout flows) without requiring CI/CD deployments. |
| **[Notification Service](./notification-service)** | `JavaMailSender`, `@Async` | High-throughput asynchronous email dispatching to prevent SMTP network latency from blocking HTTP threads. |

---

## How to Run Locally

Every project in this repository is completely self-contained and equipped with a `docker-compose.yml` file to instantly provision its necessary infrastructure (Databases, Caches, LocalStack).

### Prerequisites
- Docker Desktop
- Java 21+
- Maven 3.9+

### Execution Example (Running the Rate Limiter)
1. Navigate to the project directory:
   ```bash
   cd rate-limiter
   ```
2. Spin up the infrastructure (Redis):
   ```bash
   docker-compose up -d
   ```
3. Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

*(See the `README.md` inside each specific module for detailed API endpoints and curl commands).*

---

## Automated Testing

These projects enforce strict Test-Driven Development (TDD) using **Testcontainers**. Testcontainers dynamically spins up real, isolated Docker containers (like Redis or PostgreSQL) during the Maven `test` phase to guarantee integration tests reflect exact production behaviors.

Run the entire suite from the root directory:
```bash
mvn test
```

---

<div align="center">
  <i>Architected and Developed by <b>Kavya Siddharthan</b></i>
</div>
