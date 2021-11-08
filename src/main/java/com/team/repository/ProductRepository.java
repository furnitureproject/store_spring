package com.team.repository;

import java.util.List;

import com.team.entity.Product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

        // 시퀀스를 1 증가시키고 그 값을 받아온다.
        @Query(value = "SELECT SEQ_TEST01.nextval FROM dual", nativeQuery = true)
        Long getNextSeqVal();

        // 0. 검색

        // 0-1 최신순
        List<Product> findByProductTitleIgnoreCaseContainingOrderByProductCodeDesc(String Title, Pageable pageable);

        // 0-2 조회수순
        List<Product> findByProductTitleIgnoreCaseContainingOrderByProductHitDesc(String Title, Pageable pageable);

        // 0-3 가격 높은순, 0-4 가격 낮은순 => mybatis 사용

        // 총 개수
        long countByProductTitleIgnoreCaseContaining(String Title);

        // 1. 제품 카테고리 소분류

        // 1-1.최신순
        List<Product> findByCategory_CategoryCodeOrderByProductCodeDesc(Long categoryCode, Pageable pageable);

        // 1-2. 조회수순
        List<Product> findByCategory_CategoryCodeOrderByProductHitDesc(Long categoryCode, Pageable pageable);

        // 1-3. 가격 높은순 1-4. 가격 낮은순 => mybatis 사용

        // 총 개수
        long countByCategory_CategoryCode(Long categoryCode);

        // 2. 제품 카테고리 중분류

        // 2-1. 최신순
        List<Product> findByCategory_CategoryParentOrderByProductCodeDesc(Long categoryParent, Pageable pageable);

        // 2-2. 조회수순
        List<Product> findByCategory_CategoryParentOrderByProductHitDesc(Long categoryParent, Pageable pageable);

        // 2-3. 가격 높은순 2-4. 가격 낮은순 => mybatis 사용

        // 총 개수
        long countBycategory_CategoryParent(Long categoryParent);

        // 인기순 (판매량) -보류

}
