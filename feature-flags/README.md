# Dynamic Feature Flags

## Problem
In CI/CD, pushing code to production is easy, but rolling back a bad deployment takes time. Deployments and Feature Releases should be decoupled. If a new checkout flow causes a massive bug in production, engineers should be able to turn it off instantly without redeploying the code.

## Architecture
This project demonstrates a dynamic Feature Flag system using **Redis**. 
Services check the boolean state of a feature key in Redis before executing a specific block of code. Because Redis is an incredibly fast, in-memory store, the overhead of checking a feature flag is less than `1ms`.

## Tech Stack
* Java 21, Spring Boot 3
* Redis (Spring Data Redis)
* Docker & Docker Compose

## API Endpoints
* `POST /api/v1/features/{featureName}?enable=true` (Toggles the feature on/off instantly)
* `GET /api/v1/features/checkout` (A dummy endpoint that changes its behavior dynamically)

## How To Run
1. Start Redis:
   ```bash
   docker-compose up -d
   ```
2. Start the application:
   ```bash
   mvn spring-boot:run
   ```
3. Test the legacy flow:
   ```bash
   curl http://localhost:8086/api/v1/features/checkout
   ```
4. Turn ON the feature flag:
   ```bash
   curl -X POST http://localhost:8086/api/v1/features/new_checkout_flow?enable=true
   ```
5. Test the checkout flow again and see the instant change:
   ```bash
   curl http://localhost:8086/api/v1/features/checkout
   ```
