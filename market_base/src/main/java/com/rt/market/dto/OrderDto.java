package com.rt.market.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    @NotBlank(message = "Имя клиента не должно быть пустым")
    private String clientName;

    @NotBlank(message = "Email клиента обязателен")
    @Email(message = "Неверный формат Email")
    private String clientEmail;

    @NotBlank(message = "Адрес доставки обязателен")
    private String deliveryAddress;

    private List<OrderItemDto> items;
}
