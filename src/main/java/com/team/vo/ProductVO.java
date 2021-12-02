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
public class ProductVO {

    private Long productCode;

    private String productTitle;

    private String productDesc;

    private Integer productHit;

    private Long categoryCode;

    private Long optionPrice;

    private String image;

    private Date productRegdate;
    private String productRegdateString;

    private String optionName;


}
