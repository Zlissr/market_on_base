package com.rt.market.controller;

import com.rt.market.dto.OrderDto;
import com.rt.market.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderDto order) {
        orderService.placeOrder(order);

        return ResponseEntity.ok("Заказ успешно оформлен!");
    }
}
