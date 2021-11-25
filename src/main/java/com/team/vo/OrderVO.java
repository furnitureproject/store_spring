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
public class OrderVO {

    private Long orderNo;

    private int orderStatus;

    private Date orderDate;

    private String imageurl;

    private Long cartNo;

    private Long optionCode;

    private Long productCode;

    private String productTitle;

    private String optionName;

    private Long optionPrice;

    private Long cartOptionCount;

}
