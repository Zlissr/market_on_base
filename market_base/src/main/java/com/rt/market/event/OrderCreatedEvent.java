package com.rt.market.event;

import com.rt.market.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OrderCreatedEvent {
    private final OrderDto orderDto;
    private final BigDecimal totalPrice;
}