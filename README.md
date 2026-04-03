# 🏦 API Bank

A secure RESTful banking API built with Java and Spring Boot. Supports user registration and authentication via JWT, balance management, fund transfers, and transaction history.

---

## 🚀 Features

- **JWT Authentication** — Stateless login with token-based authorization
- **Role-Based Access Control** — `USER` and `ADMIN` roles with protected endpoints
- **Password Security** — BCrypt hashing (strength 12)
- **Banking Operations** — Cash in, cash out, and peer-to-peer transfers
- **Transaction History** — Full statement log per user (sent & received)
- **Custom Exception Handling** — Global error handler with meaningful responses
- **Profile Image Support** — Upload via file or URL

---

## 🛠️ Tech Stack

| Layer        | Technology                              |
|--------------|-----------------------------------------|
| Language     | Java 17                                 |
| Framework    | Spring Boot 3.5                         |
| Security     | Spring Security + JWT (jjwt 0.12.5)     |
| Persistence  | Spring Data JPA / Hibernate             |
| Database     | PostgreSQL                              |
| Build Tool   | Gradle                                  |
| Utilities    | Lombok                                  |

---

## 📁 Project Structure

```
src/main/java/app/bank/
├── configuration/        # JWT filter & Spring Security config
├── controller/           # REST API interfaces
│   └── impl/             # Controller implementations
├── entity/               # JPA entities (User, Statement)
├── exception/            # Custom exceptions
├── repository/           # JPA repositories
└── service/              # Business logic interfaces
    └── impl/             # Service implementations
```

---

## 📌 API Endpoints

### Auth
| Method | Endpoint               | Access  | Description         |
|--------|------------------------|---------|---------------------|
| POST   | `/bank/user/register`  | Public  | Register new user   |
| POST   | `/bank/user/login`     | Public  | Login & get JWT     |

### User
| Method | Endpoint               | Access        | Description              |
|--------|------------------------|---------------|--------------------------|
| GET    | `/bank/user/balance`   | Authenticated | Get current balance      |
| GET    | `/bank/user/statements`| Authenticated | Get transaction history  |
| DELETE | `/bank/user/delete`    | Authenticated | Delete own account       |

### Transactions
| Method | Endpoint               | Access        | Description              |
|--------|------------------------|---------------|--------------------------|
| PUT    | `/bank/user/cashIn`    | Authenticated | Deposit funds            |
| PUT    | `/bank/user/cashOut`   | Authenticated | Withdraw funds           |
| PUT    | `/bank/user/transfer`  | Authenticated | Transfer to another user |

### Admin
| Method | Endpoint               | Access  | Description        |
|--------|------------------------|---------|--------------------|
| GET    | `/bank/user/list`      | ADMIN   | List all users     |

---

## ⚙️ Getting Started

### Prerequisites
- Java 17+
- PostgreSQL
- Gradle

### 1. Clone the repository
```bash
git clone https://github.com/your-username/API-bank.git
cd API-bank
```

### 2. Configure the database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bankdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the application
```bash
./gradlew bootRun
```

The API will be available at `http://localhost:8080`.

---

## 🔐 Authentication

All protected endpoints require a Bearer token in the `Authorization` header:

```
Authorization: Bearer <your_jwt_token>
```

Obtain a token by calling `/bank/user/login` with your credentials.

---

## 🏗️ Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

- **Controller layer** — Handles HTTP requests and delegates to services
- **Service layer** — Contains all business logic
- **Repository layer** — Manages database access via JPA
- **Entity layer** — Defines the data model
