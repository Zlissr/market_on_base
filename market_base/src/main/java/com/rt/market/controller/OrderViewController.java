package com.rt.market.controller;

import com.rt.ExceptInfoUser;
import com.rt.market.dto.OrderDto;
import com.rt.market.dto.OrderItemDto;
import com.rt.market.model.ProductEntity;
import com.rt.market.service.OrderService;
import com.rt.market.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
public class OrderViewController {

    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/order-form")
    public String showOrderForm(
            @RequestParam(value = "productIds", required = false) List<Long> productIds,
             Model model
    ) {
        Map<Long, String> productIdNameMap = new HashMap<>();
        OrderDto orderDto = new OrderDto();
        orderDto.setItems(new ArrayList<>());

        List<ProductEntity> productEntities = productService.findAllById(productIds);
        productEntities.forEach(ent -> productIdNameMap.put(ent.getProductId(), ent.getName()));

        if (productIds == null || productIds.isEmpty()) {
            model.addAttribute("errorMessage", "Список товаров не заполнен");

            return "error";
        }

        productIdNameMap.forEach((id, name) ->
            orderDto.getItems().add(new OrderItemDto(id, name, 1))
        );
        model.addAttribute("orderDto", orderDto);

        return "order-form";
    }


    @PostMapping("/order")
    public String placeOrder(@Valid @ModelAttribute("orderDto") OrderDto orderDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "order-form";
        }
        try {
            orderService.placeOrder(orderDto);
            return "order-success";
        } catch (ExceptInfoUser ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "order-form";
        }
    }
}
