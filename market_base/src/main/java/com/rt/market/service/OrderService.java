package com.rt.market.service;

import com.rt.ExceptInfoUser;
import com.rt.market.Msg;
import com.rt.market.dto.OrderDto;
import com.rt.market.dto.OrderItemDto;
import com.rt.market.event.OrderCreatedEvent;
import com.rt.market.model.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ApplicationEventPublisher eventPublisher;
    private final ProductService productService;

    @Transactional
    public void placeOrder(OrderDto order) throws ExceptInfoUser {
        if (order.getItems().isEmpty()) {
            throw new ExceptInfoUser(Msg.i().getMessage("Товары не указаны"));
        }

        Set<Long> ids = new HashSet<>();
        Map<ProductEntity, Integer> productQuantityMap;
        Map<Long, Integer> itemQuantityById = new HashMap<>();

        order.getItems().forEach(item -> ids.add(item.getProductId()));
        List<ProductEntity> productList = productService.findAllById(ids.stream().toList());
        Set<ProductEntity> productSet = new HashSet<>(productList);

        for (OrderItemDto item : order.getItems()) {
            itemQuantityById.put(item.getProductId(), item.getQuantity());
        }

        productQuantityMap = createProductQuantityMap(itemQuantityById, productSet);

        productService.validateAndUpdateStock(productQuantityMap);
        BigDecimal totalPrice = calculateTotalPrice(productQuantityMap);

        eventPublisher.publishEvent(
                new OrderCreatedEvent(
                        order,
                        totalPrice
                )
        );
    }

    private Map<ProductEntity, Integer> createProductQuantityMap(
            Map<Long, Integer> itemQuantityById,
            Set<ProductEntity> productSet
    ) {
        Map<ProductEntity, Integer> productQuantityMap = new HashMap<>();

        for (ProductEntity product : productSet) {
            Integer quantity = itemQuantityById.get(product.getProductId());
            productQuantityMap.put(product, quantity);
        }

        return productQuantityMap;
    }

    private BigDecimal calculateTotalPrice(Map<ProductEntity, Integer> productQuantityMap) {
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<ProductEntity, Integer> entry : productQuantityMap.entrySet()) {
            ProductEntity product = entry.getKey();
            Integer quantity = entry.getValue();

            BigDecimal itemTotal = BigDecimal.valueOf(product.getPrice())
                    .multiply(BigDecimal.valueOf(quantity));
            total = total.add(itemTotal);
        }

        return total;
    }
}