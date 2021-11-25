package com.team.repository;

import com.team.entity.Product;
import com.team.entity.ProductProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

        // 시퀀스를 1 증가시키고 그 값을 받아온다.
        @Query(value = "SELECT SEQ_TEST01.nextval FROM dual", nativeQuery = true)
        Long getNextSeqVal();

        ProductProjection findByProductCode(Long productCode);

        // 0. 검색

        // 총 개수
        long countByProductTitleIgnoreCaseContaining(String Title);

        // 1. 제품 카테고리 소분류

        // 총 개수
        long countByCategory_CategoryCode(Long categoryCode);

        // 2. 제품 카테고리 중분류

        // 총 개수
        long countBycategory_CategoryParent(Long categoryParent);

        // 인기순 (판매량) -보류

        // 총 개수
        long countByseller_sellerId(String sellerid);

}
