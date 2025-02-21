package com.rt.market.controller;

import com.rt.ExceptInfoUser;
import com.rt.market.dto.ProductDto;
import com.rt.market.dto.ProductParamDto;
import com.rt.market.model.ProductEntity;
import com.rt.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("shop")
@RequiredArgsConstructor
public class ShopViewController {

    private final ProductService productService;

    @GetMapping("/products")
    public String listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        List<ProductDto> products = productService.getAllProducts(PageRequest.of(page, size));
        model.addAttribute("products", products);

        return "product-list";
    }

    @GetMapping("/products/{productId}")
    public String getProductParams(@PathVariable Long productId, Model model) {
        List<ProductParamDto> params;
        ProductEntity product;

        try {
            params = productService.getProductParams(productId);
            product = productService.findById(productId);

            if (params.isEmpty()) {
                return "redirect:/shop/products";
            }

            model.addAttribute("params", params);
            model.addAttribute("product", product);
        } catch (ExceptInfoUser ex) {
            model.addAttribute("errorMessage", ex.getMessage());

            return "error";
        }

        return "product-details";
    }

}
