package com.team.repository;

import java.util.List;

import com.team.entity.ProductOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    List<ProductOption> findByProduct_ProductCodeOrderByOptionCodeDesc(Long productCode);

    Long countByProduct_ProductCode(long productCode);

}
