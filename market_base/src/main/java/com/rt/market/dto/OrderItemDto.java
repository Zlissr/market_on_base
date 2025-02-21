package com.rt.market.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Long productId;
    private String productName;

    @Min(value = 1, message = "Количество должно быть не менее 1")
    private int quantity;
}
