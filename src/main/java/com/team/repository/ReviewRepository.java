package com.team.repository;

import java.util.List;

import com.team.entity.Review;
import com.team.entity.ReviewProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 상품코드에 따른 Review List
    List<ReviewProjection> findByProduct_ProductCodeOrderByReviewNumDesc(Long productCode);

    // 상품코드에 따른 Review Pagable
    Page<ReviewProjection> findByProduct_ProductCodeOrderByReviewNum(Long productCode, Pageable pageable);

    // 최신 순으로 Review List
    // List<ReviewProjection> findByOrderByReviewNum();

    @Query(value = "SELECT COUNT(REVIEW_NUM) FROM REVIEW WHERE REVIEW.PRODUCT_CODE = :no", nativeQuery = true)
    public int querySelectProductReviewCount(@Param("no") long no);

    @Query(value = "SELECT AVG(REVIEW_STAR) FROM REVIEW WHERE REVIEW.PRODUCT_CODE = :no", nativeQuery = true)
    public int querySelectProductReviewAvgStar(@Param("no") long no);

}
