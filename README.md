# 🔐 Full Stack Authentication System - Backend

## 📌 Overview

The **Full Stack Authentication System (Backend)** is a secure and scalable authentication service built to handle user registration, login, email verification, OTP validation, and password recovery.

This backend provides REST APIs for managing authentication workflows with strong validation, secure password handling, and real-time email notifications.

---

## 🚀 Features

* 📝 User Registration with validations
* 🔐 Secure Login with encrypted passwords
* 📧 Email OTP Verification
* 🔢 OTP generation & validation
* 🔔 Email notifications (Account verification, OTP, Reset password)
* 🔑 Forgot Password & Reset Password functionality
* ✅ Input validation & error handling
* 🔒 JWT-based authentication (optional if implemented)
* 🧱 Clean architecture (Controller, Service, Repository layers)

---

## 🛠️ Technologies Used

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate

### Database

* MySQL / PostgreSQL

### Email Service

* Java Mail Sender (SMTP)

### Tools

* Maven
* Lombok

---

## 📂 Project Structure

```id="x91m2k"
Authentication-System-Backend/
│
├── src/main/java/
│   ├── controller/        # API Controllers
│   ├── service/           # Business Logic
│   ├── repository/        # Database Access
│   ├── entity/            # Database Entities
│   ├── dto/               # Data Transfer Objects
│   ├── config/            # Security & App Configurations
│   └── util/              # Utility Classes (OTP, Email, etc.)
│
├── src/main/resources/
│   ├── application.properties
│
└── pom.xml                # Maven Dependencies
```

---

## ⚙️ Setup & Installation

### 1. Clone the Repository

```bash id="c3d8f1"
git clone https://github.com/your-username/Authentication-System-Backend.git
cd Authentication-System-Backend
```

### 2. Configure Database

Update `application.properties`:

```properties id="z7k2n4"
spring.datasource.url=jdbc:mysql://localhost:3306/auth_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 3. Configure Email (SMTP)

```properties id="q2v9p8"
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

> ⚠️ Use **App Passwords** instead of your real Gmail password.

---

### 4. Run the Application

```bash id="k8p1m6"
mvn spring-boot:run
```

---

## 📡 API Endpoints

### 🔹 Authentication

| Method | Endpoint             | Description       |
| ------ | -------------------- | ----------------- |
| POST   | /api/auth/register   | Register new user |
| POST   | /api/auth/login      | Login user        |
| POST   | /api/auth/verify-otp | Verify email OTP  |
| POST   | /api/auth/resend-otp | Resend OTP        |

---

### 🔹 Password Management

| Method | Endpoint                  | Description              |
| ------ | ------------------------- | ------------------------ |
| POST   | /api/auth/forgot-password | Send reset OTP via email |
| POST   | /api/auth/reset-password  | Reset password using OTP |

---

## 🔐 Authentication Flow

### 🧾 Registration Flow

1. User registers with email & password
2. System validates input data
3. OTP is generated and sent via email
4. User verifies OTP
5. Account is activated

---

### 🔑 Login Flow

1. User enters email & password
2. System validates credentials
3. JWT token (optional) is generated
4. Access granted

---

### 🔄 Forgot Password Flow

1. User requests password reset
2. OTP sent to email
3. User verifies OTP
4. User sets new password

---

## 🧪 Validation Rules (Example)

* Email must be valid format
* Password must be strong (min length, special chars, etc.)
* OTP expires after a certain time
* Duplicate email registration prevented

---

## 📈 Future Improvements

* Refresh Tokens
* Role-based authorization (Admin/User)
* OAuth2 (Google, Facebook login)
* Rate limiting & brute-force protection
* Docker deployment

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to GitHub
5. Create a Pull Request

---

## 📄 License

This project is licensed under the **MIT License**.

---

## 👨‍💻 Author

Developed by **Your Name**

---

## ⭐ Support

If you like this project, give it a ⭐ on GitHub!
