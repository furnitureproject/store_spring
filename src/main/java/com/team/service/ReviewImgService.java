package com.team.service;

import java.util.List;

import com.team.entity.ReviewImg;
import com.team.entity.ReviewImgProjection;

import org.springframework.stereotype.Service;

@Service
public interface ReviewImgService {

    // return 1
    public int insertReviewImg(List<ReviewImg> reviewImg);

    public int deleteReviewImg(Long reviewImgNum);

    // 제품에 따른 리뷰 조회
    public List<ReviewImgProjection> selectReviewImgList(Long reviewNum);

    // 리뷰 하나
    public ReviewImg selectReviewImg(Long reviewImgNum);

    // 리뷰 코드에 따른 리뷰 삭제(TEST)
    public int deleteReviewImgList(Long reviewNum);

    public Long countReviewNum(Long reviewNum);

}
