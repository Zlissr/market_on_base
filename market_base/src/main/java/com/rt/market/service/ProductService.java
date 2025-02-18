package com.rt.market.service;

import com.rt.ExceptInfoUser;
import com.rt.market.ExceptDb;
import com.rt.market.Msg;
import com.rt.market.dto.OrderItemDto;
import com.rt.market.dto.ProductDto;
import com.rt.market.dto.ProductParamDto;
import com.rt.market.model.ProductEntity;
import com.rt.market.model.ProductParamEntity;
import com.rt.market.repository.ProductParamRepository;
import com.rt.market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductParamRepository productParamRepository;

    public List<ProductDto> getAllProducts(Pageable pageable) {
        return findAll(pageable)
                .stream()
                .map(this::toProductDto)
                .collect(Collectors.toList());
    }

    public List<ProductParamDto> getProductParams(Long productId) throws ExceptInfoUser {
        ProductEntity product = findById(productId);
        List<ProductParamEntity> paramEntities = findByProduct(product);;

        return paramEntities.stream()
                .map(this::toParamDto)
                .collect(Collectors.toList());
    }

    public void validateAndUpdateStock(Map<ProductEntity, Integer> productQuantityMap) throws ExceptInfoUser {
        for (Map.Entry<ProductEntity, Integer> entry : productQuantityMap.entrySet()) {
            ProductEntity product = entry.getKey();
            int quantityNeeded = entry.getValue();

            if (product.getQuantity() < quantityNeeded) {
                throw new ExceptInfoUser(Msg.i().getMessage("Товара нет в наличии"));
            }

            reduceQuantity(product, quantityNeeded);

            save(product);
        }
    }

    private void reduceQuantity(ProductEntity product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
    }

    private ProductDto toProductDto(ProductEntity product) {
        return new ProductDto(
                product.getProductId(),
                product.getCode(),
                product.getName(),
                product.getPrice(),
                product.getQuantity()
        );
    }

    private ProductParamDto toParamDto(ProductParamEntity e) {
        return new ProductParamDto(
                e.getParamDictionary().getCode(),
                e.getParamDictionary().getLabel(),
                e.getParamValue()
        );
    }

    public ProductEntity findById(Long productId) throws ExceptInfoUser {
        try {
            return productRepository.findById(productId)
                    .orElseThrow(
                            () -> new ExceptInfoUser(Msg.i().getMessage("Услуга не найдена"))
                    );
        } catch (DataAccessException ex) {
            throw new ExceptDb("errDb04", ex.getMessage(), ex);
        }
    }

    public List<ProductEntity> findAllById(List<Long> productIds) {
        try {
            return productRepository.findAllById(productIds);
        } catch (DataAccessException ex) {
            throw new ExceptDb("errDb05", ex.getMessage(), ex);
        }
    }

    public void save(ProductEntity product) {
        try {
            productRepository.save(product);
        } catch (DataAccessException ex) {
            throw new ExceptDb("errDb06", ex.getMessage(), ex);
        }
    }

    public Page<ProductEntity> findAll(Pageable pageable) {
        try {
            return productRepository.findAll(pageable);
        } catch (DataAccessException ex) {
            throw new ExceptDb("errDb07", ex.getMessage(), ex);
        }
    }

    public List<ProductParamEntity> findByProduct(ProductEntity product) {
        try {
            return productParamRepository.findByProduct(product);
        } catch (DataAccessException ex) {
            throw new ExceptDb("errDb08", ex.getMessage(), ex);
        }
    }
}
