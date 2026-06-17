# Asynchronous Notification Service

## Problem
Calling an external third-party API (like SendGrid or AWS SES) to send an email synchronously blocks the HTTP request thread. If the email provider is slow, the user experience suffers as the frontend loader spins endlessly.

## Architecture
This project demonstrates an **Asynchronous Notification Delivery System**. 
When the client hits the API, the controller immediately returns a `202 Accepted` response. The actual heavy lifting of connecting to the SMTP server, authenticating, and sending the payload is offloaded to a background thread pool managed by Spring's `@Async`.

## Tech Stack
* Java 21, Spring Boot 3
* Spring Mail (`JavaMailSender`)
* `@Async` Thread Pool Execution

## API Endpoints
* `POST /api/v1/notifications/email?to=user@example.com&subject=Welcome` (Body: Plain Text message)

## How To Run
```bash
mvn spring-boot:run
```
Test the async behavior by sending a request and noticing that the API returns instantly, but the server logs show the email being "sent" 2 seconds later.
