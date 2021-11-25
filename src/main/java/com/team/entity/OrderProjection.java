package com.team.entity;

import java.util.Date;

public interface OrderProjection {

    Long getOrderNo();

    int getOrderState();

    Date getOrderDate();

    Long getCart_CartNo();

    // order optioncode 취합용
    Long getCart_ProductOption_OptionCode();

    // 제품 코드(이미지 불러오기 용)
    Long getCart_ProductOption_Product_ProductCode();

    // 제품 이름
    String getCart_ProductOption_Product_ProductTitle();

    // 옵션 이름
    String getCart_ProductOption_OptionName();

    // 옵션 가격
    Long getCart_ProductOption_OptionPrice();

    // 수량
    Long getCart_CartOptionCount();

}
