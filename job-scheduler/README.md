# Distributed Job Scheduler

## Problem
In a clustered microservices architecture, if you use standard Spring `@Scheduled` annotations, the scheduled task will run independently on *every* active instance of the microservice at the exact same time. This leads to redundant work, race conditions, and corrupted data (e.g., sending the same "daily digest" email 5 times because you have 5 instances running).

## Architecture
This project solves this by using **Quartz Scheduler with a JDBC JobStore**.
By pointing all Spring Boot instances to a central PostgreSQL database:
1. **Cluster Safe:** Quartz uses database row locks (`SELECT ... FOR UPDATE`) to ensure that only **one** node in the entire cluster triggers the job.
2. **Failover:** If the node currently executing the job crashes, Quartz detects it and reassigns the job to another healthy node.

## Tech Stack
* Java 21, Spring Boot 3
* Quartz Scheduler
* PostgreSQL

## How To Run
1. Start PostgreSQL:
   ```bash
   docker-compose up -d
   ```
2. Start the application:
   ```bash
   mvn spring-boot:run
   ```
3. Watch the console logs. The `SampleCronJob` will execute automatically every 15 seconds. If you spin up a second instance on another port, only one of them will grab the job.
