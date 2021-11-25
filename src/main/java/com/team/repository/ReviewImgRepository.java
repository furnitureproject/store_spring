package com.team.repository;

import java.util.List;

import com.team.entity.ReviewImg;
import com.team.entity.ReviewImgProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {

    List<ReviewImgProjection> findByReview_ReviewNum(Long reviewNum);

    void deleteByReview_ReviewNum(Long reviewNum);

    @Query(value = "SELECT COUNT(*) FROM REVIEW_IMG WHERE REVIEW_NUM=:no AND REVIEW_IMG_SIZE != 0", nativeQuery = true)
    public Long countReviewImg(@Param("no") Long no);

}
