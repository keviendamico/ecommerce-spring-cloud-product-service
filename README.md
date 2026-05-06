# ecommerce-spring-cloud-product-service

Product catalog microservice for the Spring Cloud e-commerce demo.

## What This Service Does

The product service manages the product catalog. It exposes a REST API for creating, reading, updating, and deleting products, persisting data in a PostgreSQL database via Spring Data JPA. Schema migrations are handled by Flyway. The service registers itself on the Eureka discovery server at startup and reads its configuration from the Spring Cloud Config Server, so no datasource or port properties are defined locally.

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | /api/products/ | List all products |
| GET | /api/products/{id} | Get product by id |
| POST | /api/products/ | Create a product |
| PUT | /api/products/{id} | Update a product |
| DELETE | /api/products/{id} | Delete a product |

## Key Configuration

Properties defined in `product-service.yml` on the config-repo.

| Property | Value | Why |
|----------|-------|-----|
| server.port | 8081 | Fixed port for this service |
| spring.datasource.url | jdbc:postgresql://localhost:5432/product_db | Points to the local Docker container |
| spring.datasource.username | root | Matches docker-compose credentials |
| spring.datasource.password | root | Matches docker-compose credentials |
| spring.jpa.hibernate.ddl-auto | validate | Flyway owns the schema, Hibernate only validates |
| eureka.client.service-url.defaultZone | http://localhost:8761/eureka/ | Defined in application.yml (shared) |

## How to Run

Start the PostgreSQL container first:

```
docker compose up -d
```

Then run the service (config-server and discovery-server must already be up):

```
./mvnw spring-boot:run
```

## Stack

| Component | Version |
|-----------|---------|
| Java | 21 |
| Spring Boot | 4.0.6 |
| Spring Cloud | 2025.1.1 |
| Spring Cloud Config Client | 2025.1.1 |
| Spring Cloud Netflix Eureka Client | 2025.1.1 |
| Spring Data JPA | managed by Boot |
| Flyway | managed by Boot |
| MapStruct | 1.6.3 |
| Lombok | managed by Boot |
| PostgreSQL | 14.22 (Docker) |

## Startup Order

```
1. config-server      :8888
2. discovery-server   :8761
3. product-service    :8081   <- this service
4. inventory-service  :8082
5. order-service      :8083
6. api-gateway        :8080
```

---

# Project Overview — Spring Cloud Microservices Demo

## Goal

A minimal didactic e-commerce system built to explore the core components of Spring Cloud: centralized configuration, service discovery, inter-service communication, API gateway, and resilience patterns.

## Architecture

```
[Client HTTP]
      |
[API Gateway :8080]
      |
      +---> [Product Service :8081]
      +---> [Inventory Service :8082]
      +---> [Order Service :8083]
                  |
                  +---> [Product Service]   (via OpenFeign)
                  +---> [Inventory Service] (via OpenFeign)

All services register on  --> [Eureka Discovery Server :8761]
All services read config from --> [Config Server :8888]
Config Server reads from      --> [config-repo on GitHub]
```

## Repository Structure (Polyrepo)

| # | Repository | Purpose |
|---|------------|---------|
| 1 | spring-cloud-config-repo | YAML configuration files, read by Config Server via Git |
| 2 | spring-cloud-config-server | Reads config-repo and exposes it to other services |
| 3 | spring-cloud-discovery-server | Eureka service registry |
| 4 | spring-cloud-api-gateway | Single entry point, routes to microservices |
| 5 | spring-cloud-product-service | Product catalog CRUD |
| 6 | spring-cloud-inventory-service | Inventory CRUD |
| 7 | spring-cloud-order-service | Order orchestration, calls product and inventory |

## Spring Cloud Concepts Covered

| Concept | Component | Repository |
|---------|-----------|------------|
| Centralized configuration | Spring Cloud Config | config-server + config-repo |
| Service discovery | Eureka | discovery-server |
| Client-side load balancing | Spring Cloud LoadBalancer | built into Feign and Gateway |
| Inter-service communication | OpenFeign | order-service |
| API gateway / routing | Spring Cloud Gateway | api-gateway |
| Circuit breaker | Resilience4j | order-service (optional) |

## Common Stack

```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.6</version>
</parent>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-dependencies</artifactId>
      <version>2025.1.1</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

Java: 21