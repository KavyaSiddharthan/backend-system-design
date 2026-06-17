# Distributed Lock using Redisson

## Problem
In a horizontally scaled microservices environment, multiple instances of the same service might try to execute a critical section of code (like inventory deduction) concurrently. Without synchronization across nodes, this leads to race conditions and inconsistent data (e.g., overselling tickets or inventory).

## Architecture
This project implements a **Distributed Lock** using **Redisson** and Redis. 
When a request attempts to purchase an item, it must first acquire an atomic lock from Redis using `lock.tryLock()`. All other nodes or threads trying to purchase the same item must wait. 

If a node crashes while holding the lock, Redisson's internal "watchdog" mechanism and the predefined lease time ensure the lock is automatically released, preventing system deadlocks.

## Tech Stack
* Java 21, Spring Boot 3
* Redis
* Redisson (Advanced Java Redis client)
* Docker & Docker Compose

## API Endpoints
* `POST /api/v1/orders/purchase/{productId}?quantity=1`

## How To Run
1. Start Redis:
   ```bash
   docker-compose up -d
   ```
2. Start the application:
   ```bash
   mvn spring-boot:run
   ```
3. Test concurrency by firing multiple identical requests simultaneously:
   ```bash
   curl -X POST http://localhost:8084/api/v1/orders/purchase/ITEM123 & curl -X POST http://localhost:8084/api/v1/orders/purchase/ITEM123 &
   ```
