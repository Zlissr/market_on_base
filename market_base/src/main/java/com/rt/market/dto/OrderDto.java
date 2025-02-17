package com.rt.market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String clientName;
    private String clientEmail;
    private String deliveryAddress;
    private List<OrderItemDto> items;
}
