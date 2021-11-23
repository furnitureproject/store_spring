package com.team.service;

import java.util.List;

import com.team.entity.Review;
import com.team.entity.ReviewProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    // return 1
    public int insertReview(Review review);

    // 리뷰 정보 삭제, 구현 X 사용 X, updateProduct 사용할것
    public int deleteReview(Long reviewNum);

    // 제품에 따른 리뷰 조회
    public List<ReviewProjection> selectReviewList(Long productCode);

    // 제품에 따른 리뷰 갯수
    public int countProductReview(Long productCode);

    // 리뷰 하나
    public Review selectReview(Long reviewNum);

    // 리뷰 갯수(제품 별)
    public int querySelectProductReviewCount(Long no);

    // 제품 별 리뷰 점수
    public int querySelectProductReviewAvgStar(Long no);

    // 제품에 따른 리뷰 조회(pagable)
    public Page<ReviewProjection> selectReviewPage(Long productCode, Pageable pageable);

}
