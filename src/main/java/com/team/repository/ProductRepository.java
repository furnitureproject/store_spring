package com.team.repository;

import java.util.List;

import com.team.entity.Product;
import com.team.entity.ProductProjection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

        // 시퀀스를 1 증가시키고 그 값을 받아온다.
        @Query(value = "SELECT SEQ_TEST01.nextval FROM dual", nativeQuery = true)
        String getNextSeqVal();

        // String findByProductTitleOrderByAsc(String productTitle);

        // 최신순 => 기본
        List<Product> findAllByOrderByProductCodeDesc();

        List<Product> findByProductTitleIgnoreCaseContainingOrderByProductCodeDesc(String productTitle,
                        Pageable pageable);

        List<Product> findByProductDescIgnoreCaseContainingOrderByProductCodeDesc(String productDesc,
                        Pageable pageable);

        List<Product> findByProductTitleAndProductDescIgnoreCaseContainingOrderByProductCodeDesc(String productTitle,
                        String productDesc, Pageable pageable);

        long countByProductTitleContaining(String productTitle);

        long countByProductDescContaining(String productDesc);

        // 인기순 (판매량)

        // 가격 높은순
        @Query(value = "SELECT * FROM (SELECT PRODUCT.PRODUCT_CODE, PRODUCT.PRODUCT_TITLE, PRODUCT_OPTION.OPTION_PRICE, ROW_NUMBER() OVER(PARTITION BY PRODUCT.PRODUCT_CODE ORDER BY PRODUCT_OPTION.OPTION_PRICE DESC) ROWN FROM PRODUCT, PRODUCT_OPTION WHERE PRODUCT_OPTION.PRODUCT_CODE = PRODUCT.PRODUCT_CODE) WHERE ROWN=1 ORDER BY OPTION_PRICE DESC", nativeQuery = true)
        public List<ProductProjection> querySelectPriceProductHigh();

        // 가격 낮은순
        @Query(value = "SELECT * FROM (SELECT PRODUCT.PRODUCT_CODE, PRODUCT.PRODUCT_TITLE, PRODUCT_OPTION.OPTION_PRICE, ROW_NUMBER() OVER(PARTITION BY PRODUCT.PRODUCT_CODE ORDER BY PRODUCT_OPTION.OPTION_PRICE ASC) ROWN FROM PRODUCT, PRODUCT_OPTION WHERE PRODUCT_OPTION.PRODUCT_CODE = PRODUCT.PRODUCT_CODE) WHERE ROWN=1 ORDER BY OPTION_PRICE ASC", nativeQuery = true)
        public List<ProductProjection> querySelectPriceProductLow();

        // 조회순
        List<Product> findAllByOrderByProductHitDesc();

        List<Product> findByProductTitleIgnoreCaseContainingOrderByProductHitDesc(String productTitle,
                        Pageable pageable);

        List<Product> findByProductDescIgnoreCaseContainingOrderByProductHitDesc(String productDesc, Pageable pageable);

        List<Product> findByProductTitleAndProductDescIgnoreCaseContainingOrderByProductHitDesc(String productTitle,
                        String productDesc, Pageable pageable);

}
