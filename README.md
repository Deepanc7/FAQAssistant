# 🧩 FAQ Assistant

FAQ Assistant is a **Spring Boot** backend project that allows users to manage **FAQs**, **categories**, and **tags**, and even generate **AI-powered suggested answers** using the **OpenRouter API** (supports models like LLaMA 3 and Mistral-7B).

---

## 🚀 Features

-  Manage FAQs with categories and tags  
-  Generate suggested answers using AI (OpenRouter models)  
-  Supports relationships between FAQ, Category, and Tag  
-  PostgreSQL database integration  
-  Full-text search support  
-  Built with Spring Boot 3.3 and Java 21  

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-------------|
| Language | Java 21 |
| Framework | Spring Boot 3.3 |
| Database | PostgreSQL |
| ORM | Spring Data JPA (Hibernate) |
| API | REST (Spring Web) |
| AI | OpenRouter API via WebClient |
| Build Tool | Maven |

---

## ⚙️ Getting Started

Follow these steps to get the FAQ Assistant up and running on your local machine.

---

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/Deepanc7/FAQAssistant.git
cd FAQAssistant
```

---

### 2️⃣ Set Up PostgreSQL Database

Open your PostgreSQL client (psql or pgAdmin) and create the database:
```sql
CREATE DATABASE faq_assistant;
```

**Verify the database was created:**
```bash
psql -U postgres -l
```

---

### 3️⃣ Configure Application Properties

Open `src/main/resources/application.properties` and update the following configurations:

**Database Configuration:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/faq_assistant
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

**OpenRouter API Configuration:**
```properties
openrouter.api.key=YOUR_OPENROUTER_API_KEY
openrouter.api.url=https://openrouter.ai/api/v1/chat/completions
```

> **Get your OpenRouter API Key:** Visit [openrouter.ai](https://openrouter.ai/), sign up, and generate an API key from your dashboard.

---

### 4️⃣ Build and Run the Application

**Install dependencies and build:**
```bash
mvn clean install
```

**Run the application:**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

---

### 5️⃣ Test the API with Sample Data

Now that the application is running, let's create some sample data.

#### **Step 1: Create a Category**
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Technical",
    "description": "Technical questions and answers"
  }'
```

**Response:**
```json
{
    "id": "98a259d7-5fe3-4772-a807-bbbb658edfcb",
    "name": "Technical",
    "description": "Technical questions and answers",
    "createdAt": "2025-11-03T10:49:37.395408700Z"
}
```

---

#### **Step 2: Create Tags**
```bash
curl -X POST http://localhost:8080/api/tags \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Java"
  }'
```

**Response:**
```json
{
    "id": "78620d51-429b-4973-8cba-2a1089c201e8",
    "name": "Java",
    "faqs": []
}
```

#### **Step 3: Create User**
```bash
curl -X POST http://localhost:8080/api/tags \
  -H "Content-Type: application/json" \
  -d '{
  "username": "john_doe",
  "displayName": "John Doe",
  "email": "john.doe@example.com"
}
'
```

**Response:**
```json
{
    "id": "419d16ce-488b-4223-b32e-09ac381ab171",
    "username": "john_doe",
    "displayName": "John Doe",
    "email": "john.doe@example.com",
    "createdAt": "2025-11-03T10:51:15.421504500Z"
}
```
---

#### **Step 3: Create an FAQ**

First, generate an AI answer for your question:
```bash
curl -X POST localhost:8080/api/faqs/suggest-answer?question=Can I change my registered email address? \
  -H "Content-Type: application/json" \
  -d ''
```

**Response:**
```json
Yes, you can change your registered email address. Navigate to your account settings or profile page, locate the email field, and enter your new email address. You may need to verify the new email through a confirmation link sent to your inbox before the change takes effect.
```

Now create the FAQ with the generated answer:
```bash
curl -X POST http://localhost:8080/api/faqs \
  -H "Content-Type: application/json" \
  -d '{
    "question": "Can I change my registered email address?",
    "answer": "Yes, you can change your registered email address. Navigate to your account settings or profile page, locate the email field, and enter your new email address. You may need to verify the new email through a confirmation link sent to your inbox before the change takes effect.",
    "category": {
    "id": "1007d031-8913-4260-91b0-805a5a55e0d3"
  },
  "tags": [
    { "id": "a9299f80-ab30-4c7b-a8be-8bb45f6f9281" }
  ],
  "createdBy": {
    "id": "54956013-1c3c-4f75-be5d-709b6c0bb190"
  },
  "updatedBy": {
    "id": "54956013-1c3c-4f75-be5d-709b6c0bb190"
  }
  }'
```

**Response:**
```json
{
    "id": "a39a849a-7c44-4a92-9962-5522c13558b5",
    "question": "Can I change my registered email address?",
    "answer": "Yes, go to Account Settings > Email and update your address. A verification email will be sent to confirm the change.",
    "categoryId": "98a259d7-5fe3-4772-a807-bbbb658edfcb",
    "tags": [
        null
    ],
    "createdBy": "419d16ce-488b-4223-b32e-09ac381ab171"
}
```

---

#### **Step 4: Retrieve All FAQs**
```bash
curl http://localhost:8080/api/faqs
```

---

### 6️⃣ Verify Everything Works

Open your browser and navigate to:
- **All FAQs:** `http://localhost:8080/api/faqs`
- **All Categories:** `http://localhost:8080/api/categories`
- **All Tags:** `http://localhost:8080/api/tags`

---

###  Quick Reference

**Base URL:** `http://localhost:8080`

**Key Endpoints:**
- `POST /api/categories` - Create category
- `POST /api/tags` - Create tag
- `POST /api/faqs/suggest-answer` - Generate AI answer
- `POST /api/faqs` - Create FAQ
- `GET /api/faqs` - Get all FAQs

---


### Next Steps

- 📖 Explore the [API Documentation](#-api-endpoints)
- 🧪 Run tests: `mvn test`
- 🚀 Check the [Deployment Guide](#-deployment)
