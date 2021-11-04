package com.team.vo;

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

    private Long productHit;

    private Long categoryCode;

    private byte[] thumImgData;

    private String thumImgType;

    private Long optionPrice;

}
