# Asynchronous File Processing System

## Problem
Uploading large files synchronously blocks HTTP threads, leading to poor user experience, thread starvation, and timeout errors.

## Architecture
Implements an asynchronous file processing architecture:
1. The client submits a file via POST `/upload`.
2. The controller immediately returns a `202 Accepted` with a tracking ID.
3. The `@Async` background service processes the file (e.g., compression, scanning) and streams it to AWS S3.
4. The client polls `GET /status/{id}` to check if processing is `COMPLETED`.

## Tech Stack
* Java 21, Spring Boot 3
* AWS S3 SDK v2
* LocalStack (for local AWS S3 emulation)
* Docker & Docker Compose

## API Endpoints
* `POST /api/v1/files/upload` (Requires `multipart/form-data`)
* `GET /api/v1/files/status/{fileId}`

## How To Run
```bash
docker-compose up -d
mvn spring-boot:run
```
