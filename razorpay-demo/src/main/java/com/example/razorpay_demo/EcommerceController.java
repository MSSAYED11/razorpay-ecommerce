package com.example.razorpay_demo;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller; // Changed from RestController
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class EcommerceController {

    // --- "FAKE" DATABASE (RAM) ---
    private List<Product> productCatalog = new ArrayList<>();
    private Map<String, List<CartItem>> userCarts = new HashMap<>();
    private Map<String, Order> orderDb = new HashMap<>();

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public EcommerceController() {
        // Add dummy data for the assignment requirements
        productCatalog.add(new Product("p1", "Gaming Laptop", "High performance", 50000.0, 10));
        productCatalog.add(new Product("p2", "Wireless Mouse", "Smooth scrolling", 500.0, 50));
    }

    // The Homepage (loads index.html) ---
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Razorpay Order Creation ---
    @PostMapping("/create_order")
    @ResponseBody
    public String createSimpleOrder(@RequestParam("amount") int amount) {
        try {
            RazorpayClient client = new RazorpayClient(keyId, keySecret);
            JSONObject options = new JSONObject();
            options.put("amount", amount * 100); // Paise
            options.put("currency", "INR");
            options.put("receipt", "txn_123456");
            return client.orders.create(options).toString();
        } catch (RazorpayException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    // Assignment APIs (Postman Video) ---

    //  List Products
    @GetMapping("/api/products")
    @ResponseBody
    public List<Product> getProducts() {
        return productCatalog;
    }

    //  Add to Cart
    @PostMapping("/api/cart/add")
    @ResponseBody
    public String addToCart(@RequestParam String userId, @RequestParam String productId, @RequestParam int qty) {
        userCarts.putIfAbsent(userId, new ArrayList<>());
        userCarts.get(userId).add(new CartItem(productId, qty));
        return "Item added to cart";
    }

    //  Create Order from Cart
    @PostMapping("/api/orders")
    @ResponseBody
    public Order createOrder(@RequestParam String userId) {
        List<CartItem> cart = userCarts.get(userId);
        if (cart == null || cart.isEmpty()) throw new RuntimeException("Cart is empty");

        double total = 0;
        for (CartItem item : cart) {
            // Simple logic: if p1 is 50000, else 500
            double price = item.productId.equals("p1") ? 50000.0 : 500.0;
            total += (price * item.quantity);
        }

        String orderId = UUID.randomUUID().toString();
        Order newOrder = new Order(orderId, new ArrayList<>(cart), total, "PENDING");
        orderDb.put(orderId, newOrder);
        userCarts.remove(userId); // Clear cart
        return newOrder;
    }

    //  Create Payment Link (Backend)
    @PostMapping("/api/payments/create")
    @ResponseBody
    public String createPaymentLink(@RequestParam String orderId) throws Exception {
        Order myOrder = orderDb.get(orderId);
        if (myOrder == null) return "Order not found";

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", (int)(myOrder.totalAmount * 100));
        options.put("currency", "INR");
        options.put("receipt", orderId);

        com.razorpay.Order rzpOrder = client.orders.create(options);

        myOrder.razorpayOrderId = rzpOrder.get("id");
        return rzpOrder.toString();
    }
}