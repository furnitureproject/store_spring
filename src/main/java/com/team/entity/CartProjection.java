package com.team.entity;

public interface CartProjection {

    // 카트 순서
    Long getCartNo();

    // 제품 이름
    String getProductOption_Product_ProductTitle();

    // 제품 코드
    Long getProductOption_Product_ProductCode();

    // 옵션 고른거 이름
    String getProductOption_OptionName();

    // 고른거 수량
    Long getCartOptionCount();

    // 카트 상태 (0: 카트에 직접 담은거 1: 스트레이트로 꽃은거 2: 불러오지마(주문이 진행되었거나 지웠던 데이터))
    int getCartStatus();

    // 금액
    Long getProductOption_OptionPrice();

    // 유저(카트에 담은 사람) 아이디
    String getUser_UserId();
}
