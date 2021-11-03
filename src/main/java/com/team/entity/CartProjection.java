package com.team.entity;

public interface CartProjection {

    // 카트 순서
    Long getCartNo();

    // 상품 이름
    String getProductOption_Product_ProductTitle();

    // 상품 코드
    Long getProductOption_Product_ProductCode();

    // 옵션 고른거 이름
    String getProductOption_OptionName();

    // 고른거 수량
    Long getCartOptionCount();

    // 금액
    Long getProductOption_OptionPrice();

    // 대표 사진 이름
    String getProductOption_Product_ThumImgName();

    // 대표 사진 데이터
    byte[] getProductOption_Product_ThumImgData();

    // 대표 사진 사이즈
    Long getProductOption_Product_ThumImgSize();

    // 대표 사진 타입
    String getProductOption_Product_ThumImgType();

    // 유저(카트에 담은 사람) 아이디
    // String getUser_UserId();
}
