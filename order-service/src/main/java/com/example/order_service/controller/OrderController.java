package com.example.order_service.controller;

import com.example.order_service.dto.Product;
import com.example.order_service.model.Orders;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.FeatureFlagService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderRepository repo;
    private final RestTemplate restTemplate;
    private final FeatureFlagService featureFlagService;

    @Value("${product.service.url}")
    private String productUrl;

    @GetMapping
    public List<Orders> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Orders one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Orders create(@RequestBody Orders order) {

        Product product = restTemplate.getForObject(
                productUrl + "/" + order.getProductId(),
                Product.class);

        if (product == null || product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Product unavailable");
        }

        double totalPrice = product.getPrice() * order.getQuantity();

        if (featureFlagService.isBulkDiscountEnabled() && order.getQuantity() > 5) {
            totalPrice *= 0.85;
        }

        order.setTotalPrice(totalPrice);
        order.setStatus("CREATED");

        Orders savedOrder = repo.save(order);

        if (featureFlagService.isOrderNotificationsEnabled()) {
            log.info(
                    "Order confirmed. OrderId={}, ProductDetails={{Id={}, Name={}, Price={}}}, Quantity={}, Total={}",
                    savedOrder.getId(),
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    savedOrder.getQuantity(),
                    savedOrder.getTotalPrice());
        }

        return savedOrder;
    }
}