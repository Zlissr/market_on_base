package com.rt.market.service;

import com.rt.market.dto.OrderDto;
import com.rt.market.dto.OrderItemDto;
import com.rt.market.event.OrderCreatedEvent;
import com.rt.market.model.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ApplicationEventPublisher eventPublisher;
    private final ProductService productService;

    @Transactional
    public void placeOrder(OrderDto order) {
        productService.validateAndUpdateStock(order.getItems());
        BigDecimal totalPrice = calculateTotalPrice(order.getItems());

        eventPublisher.publishEvent(
                new OrderCreatedEvent(
                        order,
                        totalPrice
                )
        );
    }

    private BigDecimal calculateTotalPrice(List<OrderItemDto> items) {
        return items.stream()
                .map(item -> {
                    ProductEntity product = productService.findById(item.getProductId());

                    return BigDecimal.valueOf(product.getPrice())
                            .multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}