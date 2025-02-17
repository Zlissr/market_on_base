package com.rt.market.repository;

import com.rt.market.model.ProductEntity;
import com.rt.market.model.ProductParamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductParamRepository extends JpaRepository<ProductParamEntity, Long> {

    List<ProductParamEntity> findByProduct(ProductEntity product);
}
