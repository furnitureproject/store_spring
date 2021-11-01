package com.team.repository;

import java.util.List;

import com.team.entity.ReviewImg;
import com.team.entity.ReviewImgProjection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {

    List<ReviewImgProjection> findByReview_ReviewNum(Long reviewNum);
}
