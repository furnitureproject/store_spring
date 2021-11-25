package com.team.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewVO {

    private Long reviewNum;

    private String reivewTitle;

    private String reviewContent;

    private int reviewStar;

    private Date reviewRegDate;
    private String reviewRegDateString;

    private String reviewImage1;

    private String reviewImage2;

    private String reviewImage3;

    private String user;

}
