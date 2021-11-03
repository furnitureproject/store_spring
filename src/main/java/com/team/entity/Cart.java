package com.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@SequenceGenerator(name = "SEQ_CART_NUM", sequenceName = "SEQ_CART_NUM", initialValue = 1, allocationSize = 1)
public class Cart {

    // 카트 순서용 넘버링
    @Id
    @Column(name = "CART_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CART_NUM")
    private Long cartNo;

    // 상품 이름
    private String cartTitle;

    // 상품 코드
    private Long cartCode;

    // 옵션 고른거 이름
    private String cartOptionName;

    // 고른거 수량
    @Column(name = "CART_QUANTITY")
    private Long cartOptionCount;

    // 금액
    private Long cartOptionPrice;

    // 대표 사진 이름
    private String cartImgName;

    // 대표 사진 데이터
    private byte[] cartImgData;

    // 대표 사진 사이즈
    private Long cartImgSize;

    // 대표 사진 타입
    private String cartImgType;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "OPTION_CODE")
    private ProductOption productOption;

}
