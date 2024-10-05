# XENT Microservice Chat Application


## Table of Contents

1. [Project overview](#Overview)
2. [Features](#Features)
3. [Technologies Used](#Technologies)
4. [Architecture](#Architecture)
5. [Microservices](#Microservices)
6. [Service Communication](#Communication)
7. [Testing](#Testing)
8. [Pipeline](#Pipeline)
9. [Deployment](#Deployment)

 
## Overview

This microservice-based application is a highly scalable, resilient, and cloud-ready chat system.
It enables users to create rooms and exchange messages via REST API or WebSockets with Kafka.
The project showcases DevOps practices with Docker, Kubernetes, CI/CD pipelines, and cloud deployment on Google Kubernetes Engine cluster.

## Features

Microservices: Four independent services communicating over HTTP and Kafka.
Asynchronous Communication: Kafka-based message queuing and event handling.
WebSocket Connetion: support of real-time messaging using WebSockets
Resilient Rollback Mechanism: Fallback mechanism to ensure data consistency.
Testing: Unit tests, Integration tests and E2E tests
CI/CD Pipeline: Automated testing, Docker image builds, and deployment.
Cloud Deployment: Scalable deployment using Google Cloud GKE and Google Cloud SQL.

## Technologies

Languages: Java
Authentication: JWT, BCrypt for password hashing
Service Discovery: Eureka (for local development), GKE for cloud
Communication: Feign Clients (HTTP), Kafka (Asynchronous), WebSockets
Containerization: Docker, Docker Compose, GHCR
Orchestration: Kubernetes (GKE)
CI/CD: GitHub Actions
Database: Google Cloud SQL, PostgreSQL
Messaging: Apache Kafka (Helm for cloud) with WebSockets
Testing: JUnit, Mockito, Rest Assured, WebSocket Client
Cloud: Google Cloud Platform (GKE, Google Cloud SQL)

## Architecture

This application follows a microservice architecture with each service handling a specific domain of responsibility. Each service follows 
Three-Tier architecture principals. Each service has its own independent PosgreSQL database, the communication is performed via Spring Data JPA and Hibernate.
For testing purposes each database has an in-memory copy (H2 Database).

## Microservices

1. API Gateway
Redirects external requests to internal services.
Manages Kafka topics and provides load balancing.
2. Authentication Service
Stores and validates user credentials.
JWT token creation and validation.
3. User Management Service
Manages user profiles, including name, description, and other settings.
4. Chat Service
Core functionality for creating rooms and handling messages.
REST API and WebSocket support via Kafka for real-time messaging.

## Communication

HTTP with Eureka and Feign: Services communicate via HTTP using Eureka for service discovery in local and CI environment.
Kafka Asynchronous Messaging: For efficiency and scalability, services also use Kafka for asynchronous messaging wherever possible.

## Testing

Unit Testing: Each service has comprehensive unit tests (JUnit, Mockito).
Integration Testing: Integration tests cover the interaction between services.
End-to-End (E2E) Testing: Using Rest Assured for HTTP and WebSocket Client for WebSocket-based interactions.

## Pipeline

The CI/CD pipeline ensures seamless integration and continuous delivery using GitHub Actions. The pipeline operates in two modes:

Feature/Development Branches:
Builds and tests all services using matrix jobs for parallel execution.
Publishes Docker images to GitHub Container Registry.
Runs end-to-end tests with Docker Compose.
Logs are captured as artifacts for troubleshooting.

Deployment Branch:
Deploys the application to GKE by applying Kubernetes manifests.
Uses Google Cloud's Autopilot mode for scaling and managing Kubernetes resources.
Manages secrets and credentials for secure access to Google Cloud SQL and APIs.

## Deployment

Local Deployment
Run services in Docker containers with Docker Compose.
Build or pull Docker images for services and dependencies like Kafka and PostgreSQL.

Cloud Deployment (GKE)
The application is deployed on GKE, with services registered using GKE Service Discovery.
Database: Google Cloud SQL.
Kafka: Deployed using Helm charts for cluster-wide asynchronous communication.


# License

This project is licensed under the MIT License. See the LICENSE file for details.
