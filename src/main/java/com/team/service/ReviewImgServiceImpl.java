package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.ReviewImg;
import com.team.entity.ReviewImgProjection;
import com.team.repository.ReviewImgRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewImgServiceImpl implements ReviewImgService {

    @Autowired
    ReviewImgRepository rIRepository;

    @Override
    public int insertReviewImg(List<ReviewImg> reviewImg) {
        rIRepository.saveAll(reviewImg);
        return 1;
    }

    @Override
    public int deleteReviewImg(Long reviewImgNum) {
        rIRepository.deleteById(reviewImgNum);
        return 1;
    }

    @Override
    public List<ReviewImgProjection> selectReviewImgList(Long reviewNum) {

        return rIRepository.findByReview_ReviewNum(reviewNum);
    }

    @Override
    public ReviewImg selectReviewImg(Long reviewImgNum) {
        Optional<ReviewImg> rImg = rIRepository.findById(reviewImgNum);
        return rImg.orElse(null);
    }

    @Override
    public int deleteReviewImgList(Long reviewNum) {
        rIRepository.deleteByReview_ReviewNum(reviewNum);
        return 1;
    }

    @Override
    public Long countReviewNum(Long reviewNum) {
        return rIRepository.countReviewImg(reviewNum);
    }

}
