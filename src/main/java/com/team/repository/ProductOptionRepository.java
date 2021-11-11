package com.team.repository;

import java.util.List;

import com.team.entity.OptionProjection;
import com.team.entity.ProductOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    List<OptionProjection> findByProduct_ProductCodeOrderByOptionCodeAsc(Long productCode);

    Long countByProduct_ProductCode(long productCode);

}
