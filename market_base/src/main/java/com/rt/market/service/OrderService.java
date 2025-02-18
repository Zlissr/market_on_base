package com.rt.market.service;

import com.rt.ExceptInfoUser;
import com.rt.market.dto.OrderDto;
import com.rt.market.dto.OrderItemDto;
import com.rt.market.event.OrderCreatedEvent;
import com.rt.market.model.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ApplicationEventPublisher eventPublisher;
    private final ProductService productService;

    @Transactional
    public void placeOrder(OrderDto order) throws ExceptInfoUser {
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
        List<Long> ids = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        items.forEach(item -> ids.add(item.getProductId()));
        List<ProductEntity> productEntities = productService.findAllById(ids);

        if (!productEntities.isEmpty()) {
            for (ProductEntity product : productEntities) {
                BigDecimal itemTotal = BigDecimal.valueOf(product.getPrice())
                        .multiply(BigDecimal.valueOf(product.getQuantity()));
                total = total.add(itemTotal);
            }
        }

        return total;
    }
}