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

        // 최신순
        // 제품 카테고리별 제목 검색
        List<Product> findByCategory_CategoryCodeAndProductTitleIgnoreCaseContainingOrderByProductCodeDesc(
                        Long categoryCode, String productTitle);

        long countByCategory_CategoryCodeAndProductTitleContaining(Long categoryCode, String productTitle);

        // 제목 카테고리별 내용 검색
        List<Product> findByCategory_CategoryCodeAndProductDescIgnoreCaseContainingOrderByProductCodeDesc(
                        Long categoryCode, String productDesc);

        // // 제목 카테고리별 제목+내용 검색
        List<Product> findByCategory_CategoryCodeAndProductTitleAndAndProductDescIgnoreCaseContainingOrderByProductCodeDesc(
                        String productTitle, Long categoryCode, String productDesc);

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

        // // @Query(value = "SELECT * FROM PRODUCT WHERE CATEGORY3_CODE=:code")
        // // List<Product> category3Select(@Param("code") long code);

        // // @Query(value = "SELECT PRODUCT.PRODUCT_CODE,
        // // PRODUCT.PRODUCT_TITLE,PRODUCT.PRODUCT_DESC,"
        // // + "PRODUCT.PRODUCT_HIT,
        // // CATEGORY3.CATEGORY3_CODE,PRODUCT.SELLER_ID,CATEGORY3.CATEGORY3_NAME,"
        // // + " CATEGORY3.CATEGORY2_CODE FROM PRODUCT, CATEGORY3 "
        // // + " WHERE PRODUCT.CATEGORY3_CODE=CATEGORY3.CATEGORY3_CODE AND
        // // CATEGORY2_CODE=:code")
        // // List<Product> category2Select(@Param("code") long code);

        // // @Query(value = "SELECT * FROM CATEGORY2, (SELECT PRODUCT.PRODUCT_CODE, "
        // // + "PRODUCT.PRODUCT_TITLE, PRODUCT.PRODUCT_DESC, PRODUCT.PRODUCT_HIT, "
        // // + "CATEGORY3.CATEGORY3_CODE, PRODUCT.SELLER_ID, CATEGORY3.CATEGORY3_NAME,
        // "
        // // + "CATEGORY3.CATEGORY2_CODE FROM PRODUCT, CATEGORY3 "
        // // + "WHERE PRODUCT.CATEGORY3_CODE=CATEGORY3.CATEGORY3_CODE) CATE "
        // // + "WHERE CATEGORY2.CATEGORY2_CODE= CATE.CATEGORY2_CODE
        // // ANDCATEGORY1_CODE=:code")
        // // List<Product> category1Select(@Param("code") long code);

        // // 카테고리3분류 최신순
        // // 1. 제목 검색
        // // @Query(value = "SELECT PRODUCT.PRODUCT_CODE, PRODUCT.PRODUCT_TITLE,"
        // // + " PRODUCT.PRODUCT_HIT, PRODUCT.CATEGORY3_CODE, PRODUCT.PRODUCT_DESC, "
        // // + " PRODUCT_THUMNAIL.THUM_IMG_DATA, PRODUCT_THUMNAIL.THUM_IMG_TYPE FROM
        // // PRODUCT,PRODUCT_THUMNAIL "
        // // + " WHERE PRODUCT.PRODUCT_CODE=PRODUCT_THUMNAIL.PRODUCT_CODE AND
        // // CATEGORY3_CODE=:code"
        // // + " AND PRODUCT_TITLE LIKE '%' || :title || '%' ORDER BY PRODUCT_CODE
        // DESC",
        // // nativeQuery = true)
        // // public List<ProductProjection1> category3TitleSelect(@Param("code") long
        // // code, @Param("title") String title);

        // // long countByProducttitleAndCategory3codeContaining(String productTitle,
        // long
        // // category3code);

        // // 2. 내용 검색

        // @Query(value = "SELECT PRODUCT.PRODUCT_CODE, PRODUCT.PRODUCT_TITLE,"
        // + " PRODUCT.PRODUCT_HIT, PRODUCT.CATEGORY3_CODE, PRODUCT.PRODUCT_DESC, "
        // + " PRODUCT_THUMNAIL.THUM_IMG_DATA, PRODUCT_THUMNAIL.THUM_IMG_TYPE FROM
        // PRODUCT,PRODUCT_THUMNAIL"
        // + " WHERE PRODUCT.PRODUCT_CODE=PRODUCT_THUMNAIL.PRODUCT_CODE AND
        // CATEGORY3_CODE=:code"
        // + " AND PRODUCT_DESC LIKE '%' || :desc || '%' ORDER BY PRODUCT_CODE DESC",
        // nativeQuery = true)
        // public List<ProductProjection1> category3DescSelect(@Param("code") long code,
        // @Param("desc") String desc);

        // // List<ProductProjection1>
        // //
        // findByCategory3_Category2_Category1_Category1CodeOrderByCategory3_Category2_Category1_Category1Code(
        // // long code);

        // // 3. 제목 + 내용검색
        // @Query(value = "SELECT PRODUCT.PRODUCT_CODE, PRODUCT.PRODUCT_TITLE,"
        // + " PRODUCT.PRODUCT_HIT, PRODUCT.CATEGORY3_CODE, PRODUCT.PRODUCT_DESC, "
        // + " PRODUCT_THUMNAIL.THUM_IMG_DATA, PRODUCT_THUMNAIL.THUM_IMG_TYPE FROM
        // PRODUCT,PRODUCT_THUMNAIL"
        // + " WHERE PRODUCT.PRODUCT_CODE=PRODUCT_THUMNAIL.PRODUCT_CODE AND
        // CATEGORY3_CODE=:code"
        // + " AND PRODUCT_DESC LIKE '%' || :desc || '%' AND PRODUCT_TITLE LIKE '%' ||
        // :title || '%' ORDER BY PRODUCT_CODE DESC", nativeQuery = true)
        // public List<ProductProjection1> category3TitleDescSelect(@Param("code") long
        // code, @Param("desc") String desc,
        // @Param("title") String title);

}
