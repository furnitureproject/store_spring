package com.team.entity;

import java.util.Date;

public interface ReviewProjection {

    Long getReviewNum();

    String getReviewTitle();

    String getReviewContent();

    Integer getReviewStar();

    // String getReviewImgName();

    // String getReviewImgData();

    // Long getReplyImgSize();

    // String getReplyImgType();

    Date getReviewRegDate();

    String getUser_UserId();

    // Long getProduct_ProductCode();

}
