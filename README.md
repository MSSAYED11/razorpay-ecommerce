# Spring Boot E-Commerce & Razorpay Integration

This project is a minimal E-commerce backend system built with **Spring Boot** and integrated with **Razorpay** for payment processing. It demonstrates the complete flow of an e-commerce application, including product listing, shopping cart management, order creation, and online payment handling.

## 🚀 Features

* **Product Catalog:** REST APIs to list available products.
* **Shopping Cart:** In-memory management of user carts (add items, view cart).
* **Order Management:** conversion of cart items into secure Orders with unique IDs.
* **Payment Gateway:** Full integration with **Razorpay** (Test Mode) to accept payments via Netbanking, UPI, etc.
* **Frontend:** A Thymeleaf-based UI to demonstrate the checkout flow.

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x / 4.x
* **Payment Gateway:** Razorpay Java SDK
* **Template Engine:** Thymeleaf
* **Tools:** Maven, Postman

## ⚙️ Setup Instructions

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/YOUR_USERNAME/razorpay-ecommerce.git](https://github.com/YOUR_USERNAME/razorpay-ecommerce.git)
    ```
2.  **Configure API Keys:**
    Open `src/main/resources/application.properties` and add your Razorpay Test Keys:
    ```properties
    razorpay.key.id=YOUR_KEY_ID
    razorpay.key.secret=YOUR_KEY_SECRET
    ```
3.  **Run the Application:**
    Run the `RazorpayDemoApplication.java` file from your IDE (IntelliJ/Eclipse).
4.  **Access the App:**
    * **Frontend:** Open `http://localhost:8080` in your browser.
    * **Backend APIs:** Use Postman to test endpoints (see below).

## 🔌 API Documentation

### 1. Product APIs
* **List All Products**
    * `GET /api/products`
    * Returns a list of products (e.g., Gaming Laptop, Wireless Mouse).

### 2. Cart APIs
* **Add Item to Cart**
    * `POST /api/cart/add?userId={id}&productId={id}&qty={number}`
    * Adds a specific product to the user's temporary cart.

### 3. Order APIs
* **Create Order**
    * `POST /api/orders?userId={id}`
    * Converts the user's cart into an Order object and returns the Order details (ID, Total Amount, Status).

### 4. Payment APIs
* **Initiate
