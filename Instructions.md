# Setup, Build, and Run Instructions for Popcorn Palace Movie Ticket Booking System

## Prerequisites

Make sure you have the following tools installed:

- **Java 21**
- **Maven 3.6+**
- **Docker & Docker Compose**
- **Git**

## Clone the Project

```bash
git clone <repo-url>
cd popcorn-palace
```

## Database Setup

This project uses PostgreSQL. A pre-configured `compose.yml` is provided to run the database locally.

### Step 1: Start PostgreSQL via Docker Compose

```bash
docker-compose up -d
```

This will spin up a PostgreSQL container with the following configuration (as defined in the `compose.yml` file):

```
host: localhost
port: 5433
database: popcorn-palace
db user: popcorn-palace
db password: popcorn-palace
```

Make sure your `application.yml` file reflects the same credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/popcorn-palace
    username: popcorn-palace
    password: popcorn-palace
```

### Step 2: Configure Schema Generation

In the `application.yml` file:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: create
```

Run the application once to generate the schema. Then **update** the setting to:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: none
```

This prevents unwanted schema updates in subsequent runs.

---
## Test the Application


```bash
./mvnw clean test   #for linux
.\mvnw clean test   #for window
```

Or, if Maven is installed globally:

```bash
mvn clean test
```

---

---

## Build the Application

```bash
./mvnw clean install   #for linux
.\mvnw clean install   #for windows
```

Or, if Maven is installed globally:

```bash
mvn clean install
```

---

## Run the Application

```bash
./mvnw spring-boot:run
```

Or:

```bash
java -jar target/popcorn-palace-0.0.1-SNAPSHOT.jar
```
