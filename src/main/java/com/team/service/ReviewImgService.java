package com.team.service;

import java.util.List;

import com.team.entity.ReviewImg;
import com.team.entity.ReviewImgProjection;

import org.springframework.stereotype.Service;

@Service
public interface ReviewImgService {

    // return 1
    public int insertReviewImg(List<ReviewImg> reviewImg);

    // 리뷰 정보 삭제, 구현 X 사용 X, updateProduct 사용할것
    public int deleteReview(Long reviewImgNum);

    // 제품에 따른 리뷰 조회
    public List<ReviewImgProjection> selectReviewList(Long reviewNum);

    // 리뷰 하나
    public ReviewImg selectReview(Long reviewImgNum);

}
