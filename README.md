# Microservices CI/CD Project – PROG3360 (Assignment Two)

**Group No:** 10  
**Course:** Software Delivery and Release Management (PROG3360) – Winter 2026 – Section 1

**Team Members:**
- Shubham Narendrakumar Savaliya – 8966303
- Pratham Bhatiya – 8960915
- Riya Brahmbhatt – 8979551

**Repository:** [https://github.com/ShubhamSavaliya26/Microservices-CI-CD](https://github.com/ShubhamSavaliya26/Microservices-CI-CD)

---

# Project Overview

This project extends the e-commerce microservices system from Assignment One by implementing **Feature Flags** using **Unleash**. The complete infrastructure is containerized with Docker, featuring PostgreSQL for Unleash, and automated via a GitHub Actions CI/CD pipeline.

### Core Components:
1.  **Product Service** (Port 8081): Manages product data and premium pricing logic.
2.  **Order Service** (Port 8082): Manages order creation, notifications, and bulk discounts.
3.  **Unleash Server** (Port 4242): Feature flag management interface.
4.  **PostgreSQL** (Port 5432): Database for Unleash.

---

# Getting Started

### 1. Run the Infrastructure
Clone the repository and start the complete stack using Docker Compose:

```bash
git clone https://github.com/ShubhamSavaliya26/Microservices-CI-CD
cd Microservices-CI-CD
docker-compose up --build
```

### 2. Configure Unleash Feature Flags

1.  Open your browser and navigate to **[http://localhost:4242](http://localhost:4242)**.
2.  Log in with the default credentials:
    *   **Username:** `admin`
    *   **Password:** `admin123` or `unleash4all`
3.  **Generate an Admin Token:**
    *   Click the **Admin** button (top right).
    *   Go to **Project Settings** -> **API Access**.
    *   Click **Create API token**.
    *   Select **Admin Token**, give it a name, and copy the generated token.
4.  **Export the Token and Initialize Flags:**
    In your Git Bash or terminal, run:
    ```bash
    export UNLEASH_ADMIN_TOKEN="<your_copied_token>"
    bash ./scripts/init-flags.sh
    ```
5.  Verify in the Unleash Dashboard that the three flags (`premium-pricing`, `order-notifications`, `bulk-order-discount`) are created.

---

# Feature Flags & API Testing

### Feature 1: Premium Pricing (Product Service)
*   **Flag Name:** `premium-pricing`
*   **Endpoint:** `GET http://localhost:8081/api/products/premium`
*   **Behavior:**
    *   **OFF:** Returns products at regular prices.
    *   **ON:** Returns products with a 10% discount applied.

### Feature 2: Order Notifications (Order Service)
*   **Flag Name:** `order-notifications`
*   **Endpoint:** `POST http://localhost:8082/api/orders`
*   **Behavior:**
    *   **OFF:** Creates order silently.
    *   **ON:** Logs a notification message in the service logs including Order ID, product details, and total price.

### Feature 3: Bulk Order Discount (Order Service)
*   **Flag Name:** `bulk-order-discount`
*   **Endpoint:** `POST http://localhost:8082/api/orders`
*   **Behavior:**
    *   **OFF:** Calculates `totalPrice` normally.
    *   **ON:** Applies a 15% discount if the quantity is greater than 5.

---

# API Reference

### Product Service (Port 8081)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/api/products` | List all products (Regular Prices) |
| GET | `/api/products/premium` | List products (Affected by `premium-pricing` flag) |
| POST | `/api/products` | Add a new product |

### Order Service (Port 8082)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/api/orders` | List all orders |
| POST | `/api/orders` | Create a new order (Affected by `order-notifications` & `bulk-order-discount`) |

---

# Sample Requests (Postman / cURL)

**Create Product:**
```bash
curl -X POST http://localhost:8081/api/products \
-H "Content-Type: application/json" \
-d '{"name": "Laptop", "price": 1000.0, "quantity": 10}'
```

**Create Order:**
```bash
curl -X POST http://localhost:8082/api/orders \
-H "Content-Type: application/json" \
-d '{"productId": 1, "quantity": 6, "customerName": "John Doe"}'
```

---

# CI/CD Pipeline

The project includes a GitHub Actions workflow (`.github/workflows/ci.yml`) that:
1.  **Builds & Tests** the microservices.
2.  Starts the full stack via **Docker Compose**.
3.  **Initializes Flags** and runs integration tests to verify flag toggling and service behavior.
