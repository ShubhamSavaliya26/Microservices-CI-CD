# Microservices CI/CD Project – PROG3360

**Group No:** 10  
**Course:** Software Delivery and Release Management (PROG3360) – Winter 2026 – Section 1

**Team Members:**
- Shubham Narendrakumar Savaliya – 8966303
- Pratham Bhatiya – 8960915
- Riya Brahmbhatt – 8979551

**Repository:** https://github.com/ShubhamSavaliya26/Microservices-CI-CD



# Project Overview

This project demonstrates a simple e-commerce backend system built with two **Spring Boot microservices**, containerized using **Docker Compose**, and automated with a **CI/CD pipeline** using GitHub Actions.

The system consists of:

1. **Product Service** – Manages product data (CRUD).
2. **Order Service** – Manages order creation and retrieval.



# Getting Started

1. **Run the Application**
   ```bash
   git clone https://github.com/ShubhamSavaliya26/Microservices-CI-CD
   cd Microservices-CI-CD
Build and start services

docker-compose up --build
Verify services

Product Service: http://localhost:8080

Order Service: http://localhost:8082


2. **API Endpoints**

Product Service
Method	Endpoint	Description
GET	/api/products	List all products
GET	/api/products/{id}	Get product by ID
POST	/api/products	Create new product
DELETE	/api/products/{id}	Delete a product

Order Service
Method	Endpoint	Description
GET	/api/orders	List all orders
GET	/api/orders/{id}	Get order by ID
POST	/api/orders	Create new order


3. **TRY in Postman:**

***ProductService:***
POST http://localhost:8080/products
Content-Type: application/json

{
"name": "Laptop",
"price": 1200.00,
"quantity": 10
}

***OrderService:***
POST http://localhost:8082/orders
Content-Type: application/json

{
"productId": 1,
"quantity": 2
}