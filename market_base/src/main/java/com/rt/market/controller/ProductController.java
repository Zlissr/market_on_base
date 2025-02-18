package com.rt.market.controller;

import com.rt.market.dto.ProductDto;
import com.rt.market.dto.ProductParamDto;
import com.rt.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ProductDto> products = productService.getAllProducts(PageRequest.of(page, size));

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}/params")
    public ResponseEntity<List<ProductParamDto>> getProductParams(@PathVariable Long productId) {
        List<ProductParamDto> params = productService.getProductParams(productId);

        return ResponseEntity.ok(params);
    }
}
