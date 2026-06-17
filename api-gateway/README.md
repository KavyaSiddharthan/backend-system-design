# API Gateway & Circuit Breaker

## Problem
In a microservices architecture, clients shouldn't need to know the IP address or port of every single service. Furthermore, if a downstream service (like the Rate Limiter or Distributed Lock) crashes or hangs, the client shouldn't hang indefinitely. 

## Architecture
This project serves as the central entry point for all API requests using **Spring Cloud Gateway**.
It provides:
1. **Dynamic Routing:** Forwards `/api/v1/limited/**` to the `rate-limiter` service (running on port 8082).
2. **Circuit Breaking:** Uses **Resilience4j**. If the downstream service fails repeatedly or times out (after 4 seconds), the circuit opens. The Gateway immediately returns a predefined HTTP 503 fallback response instead of waiting for the downstream service.

## Tech Stack
* Java 21, Spring Boot 3
* Spring Cloud Gateway (Reactive)
* Resilience4j (Circuit Breaker & Time Limiter)

## How To Run
```bash
mvn spring-boot:run
```

To test the Circuit Breaker:
1. Start this API Gateway.
2. DO NOT start the `rate-limiter` service.
3. Make a request to `http://localhost:8080/api/v1/limited`
4. Notice how the Gateway catches the connection refused error and returns the clean string from `FallbackController.java`.
