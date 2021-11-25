package com.team.repository;

import java.util.List;

import com.team.entity.OptionProjection;
import com.team.entity.ProductOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    List<OptionProjection> findByProduct_ProductCodeOrderByOptionCodeAsc(Long productCode);

    Long countByProduct_ProductCode(long productCode);

    @Query(value = "SELECT  MIN(OPTION_PRICE) FROM PRODUCT_OPTION  , PRODUCT WHERE PRODUCT.PRODUCT_CODE= PRODUCT_OPTION.PRODUCT_CODE AND PRODUCT.PRODUCT_CODE =:no ", nativeQuery = true)
    public Long SelectPriceLow(@Param("no") long no);

}
