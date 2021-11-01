package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.Review;
import com.team.entity.ReviewProjection;
import com.team.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository rRepository;

    @Override
    public int insertReview(Review review) {
        rRepository.save(review);
        return 1;
    }

    @Override
    public int deleteReview(Long reviewNum) {
        rRepository.deleteById(reviewNum);
        return 1;
    }

    @Override
    public List<ReviewProjection> selectReviewList(Long productCode) {
        return rRepository.findByProduct_ProductCodeOrderByReviewNum(productCode);
    }

    @Override
    public int countProductReview(Long productCode) {

        return rRepository.findByProduct_ProductCodeOrderByReviewNum(productCode).size();
    }

    @Override
    public Review selectReview(Long reviewNum) {
        Optional<Review> review = rRepository.findById(reviewNum);
        return review.orElse(null);
    }

}
