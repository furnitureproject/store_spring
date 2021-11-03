package com.team.repository;

import java.util.List;

import com.team.entity.Product;
import com.team.entity.ProductProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

        // 시퀀스를 1 증가시키고 그 값을 받아온다.
        @Query(value = "SELECT SEQ_TEST01.nextval FROM dual", nativeQuery = true)
        String getNextSeqVal();

        // 0. 검색

        // 0-1 최신순
        List<Product> findByProductTitleIgnoreCaseContainingOrderByProductCodeDesc(String productTitle);

        // 0-2 조회수순
        List<Product> findByProductTitleIgnoreCaseContainingOrderByProductHitDesc(String productTitle);

        // 0-3 가격 높은순

        // 0-4 가격 낮은순

        // 총 개수
        long countByProductTitleIgnoreCaseContaining(String productTitle);

        // 1. 제품 카테고리 소분류

        // 1-1.최신순
        List<Product> findByCategory_CategoryCodeOrderByProductCodeDesc(Long categoryCode);

        // 1-2. 조회수순
        List<Product> findByCategory_CategoryCodeOrderByProductHitDesc(Long categoryCode);

        // 1-3. 가격 높은순

        // 1-4. 가격 낮은순

        // 총 개수
        long countByCategory_CategoryCode(Long categoryCode);

        // 2. 제품 카테고리 중분류

        // 2-1. 최신순
        List<Product> findByCategory_CategoryParentOrderByProductCodeDesc(Long categoryParent);

        // 2-2. 조회수순
        List<Product> findByCategory_CategoryParentOrderByProductHitDesc(Long categoryParent);

        // 2-3. 가격 높은순

        // 2-4. 가격 낮은순

        // 총 개수
        long countBycategory_CategoryParent(Long categoryParent);

        // 인기순 (판매량) -보류

        // 가격 높은순
        @Query(value = "SELECT * FROM (SELECT PRODUCT.PRODUCT_CODE, PRODUCT.PRODUCT_TITLE, PRODUCT_OPTION.OPTION_PRICE, ROW_NUMBER() OVER(PARTITION BY PRODUCT.PRODUCT_CODE ORDER BY PRODUCT_OPTION.OPTION_PRICE DESC) ROWN FROM PRODUCT, PRODUCT_OPTION WHERE PRODUCT_OPTION.PRODUCT_CODE = PRODUCT.PRODUCT_CODE) WHERE ROWN=1 ORDER BY OPTION_PRICE DESC", nativeQuery = true)
        public List<ProductProjection> querySelectPriceProductHigh();

        // 가격 낮은순
        @Query(value = "SELECT * FROM (SELECT PRODUCT.PRODUCT_CODE, PRODUCT.PRODUCT_TITLE, PRODUCT_OPTION.OPTION_PRICE, ROW_NUMBER() OVER(PARTITION BY PRODUCT.PRODUCT_CODE ORDER BY PRODUCT_OPTION.OPTION_PRICE ASC) ROWN FROM PRODUCT, PRODUCT_OPTION WHERE PRODUCT_OPTION.PRODUCT_CODE = PRODUCT.PRODUCT_CODE) WHERE ROWN=1 ORDER BY OPTION_PRICE ASC", nativeQuery = true)
        public List<ProductProjection> querySelectPriceProductLow();

}
