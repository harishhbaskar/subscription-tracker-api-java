# 🗓️ Subscription Tracker API

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-brightgreen.svg)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue.svg)
![MariaDB](https://img.shields.io/badge/MariaDB-Supported-blue.svg)

A production-ready REST API for tracking personal subscriptions. Built with **Java 21** and **Spring Boot 3**, this project demonstrates modern backend architecture, secure stateless authentication, and robust data validation.

---

## ✨ Key Features

*   **Stateless Authentication**: Custom JWT (JSON Web Token) implementation with Spring Security.
*   **Layered Architecture**: Strict separation of concerns (Controller → Service → Repository).
*   **Advanced Data Filtering**: Dynamic database queries using JPA Criteria API / Specifications.
*   **Security & Hardening**: IP-based rate limiting (Bucket4j) and global exception handling.
*   **Interactive Documentation**: Auto-generated OpenAPI (Swagger) UI.
*   **Environment Isolation**: Twelve-Factor App methodology using `.env` configurations.

---

## 🏗️ Architecture

This application follows the standard Enterprise Java Layered Architecture, ensuring that HTTP parsing, business logic, and database access are strictly isolated.

*(Insert your Excalidraw Architecture Diagram here)*

---

## 🛠️ Tech Stack

*   **Core**: Java 21, Spring Boot 3.3.2
*   **Security**: Spring Security, jjwt (JSON Web Tokens), Bucket4j (Rate Limiting)
*   **Database**: Spring Data JPA, Hibernate, MariaDB (MySQL compatible)
*   **Documentation**: Springdoc OpenAPI (Swagger UI)
*   **Testing**: JUnit 5, Mockito
*   **Utilities**: Lombok, Spring Dotenv

---

## 🚀 Local Setup Guide

Follow these steps to get the API running on your local machine.

### Prerequisites
*   [Java Development Kit (JDK) 21+](https://adoptium.net/)
*   [Maven](https://maven.apache.org/download.cgi)
*   [MariaDB](https://mariadb.org/) or MySQL installed and running locally.

### 1. Database Setup
Create a new database for the application:
```sql
CREATE DATABASE subscription_tracker;
```

### 2. Configure Environment Variables
This project uses a `.env` file to keep secrets out of source control. 
Duplicate the `.env.example` file and rename it to `.env`:

```bash
cp .env.example .env
```
Open `.env` and fill in your actual database credentials:
```ini
DB_USERNAME=root
DB_PASSWORD=your_super_secret_password
JWT_SECRET=generate_a_very_long_random_string_for_this_secret_key
```

### 3. Run the Application
You can run the application directly using the Maven wrapper:

```bash
mvn clean spring-boot:run
```
*(Hibernate will automatically generate the required database tables on startup based on the JPA `@Entity` definitions).*

---

## 📖 API Documentation (Swagger UI)

Once the application is running, you don't need to guess the endpoints or request bodies! The API is fully self-documenting.

Navigate to the following URL in your browser to access the interactive Swagger UI:

👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

### Testing with Swagger
1.  Navigate to `POST /auth/register` to create an account.
2.  Copy the `token` from the response.
3.  Scroll to the top of the Swagger page and click the green **"Authorize"** button.
4.  Paste your token and click Authorize.
5.  You can now interact with all the protected `/api/v1/subscriptions` endpoints directly from the browser!

---

## 📂 Project Structure

```text
src/main/java/com/harish/subscriptiontracker/
├── controller/         # HTTP request handlers and Swagger annotations
├── dto/                # Data Transfer Objects (Request/Response shapes & validation)
├── entity/             # JPA Database Entities (Subscription, User)
├── exception/          # Custom exceptions and Global @RestControllerAdvice
├── repository/         # Spring Data JPA interfaces and dynamic Specifications
├── security/           # JWT Filters, Rate Limiting, and SecurityConfig
└── service/            # Core business logic and database orchestration
```

---

## 🧪 Testing

The project includes isolated unit tests for core business logic, demonstrating enterprise mocking strategies without needing a live database.

To run the test suite:
```bash
mvn test
```
