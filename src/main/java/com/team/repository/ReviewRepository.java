package com.team.repository;

import java.util.List;

import com.team.entity.Review;
import com.team.entity.ReviewProjection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 상품코드에 따른 Review List
    List<ReviewProjection> findByProduct_ProductCodeOrderByReviewNum(Long productCode);

    // 최신 순으로 Review List
    // List<ReviewProjection> findByOrderByReviewNum();

}
