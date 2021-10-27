package com.team.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "PRODUCT")
public class Product {

    // 등록일자 + seq 로 코드를 할 예정입니다.
    @Id
    @Column(name = "PRODUCT_CODE")
    private Long productCode;

    @Column(name = "PRODUCT_TITLE")
    private String productTitle;

    @Column(name = "PRODUCT_DESC")
    private String productDesc = null;

    @Column(name = "PRODUCT_HIT")
    private int productHit = 0;

    @CreationTimestamp
    @Column(name = "PRODUCT_REGDATE", updatable = false)
    private Date productRegdate;

    @Column(name = "PRODUCT_EDITDATE", nullable = true)
    private Date productEditdate;

    @ManyToOne
    @JoinColumn(name = "CATEGORY3_CODE")
    private Category3 category3;

    @ManyToOne
    @JoinColumn(name = "SELLER_ID")
    private Seller seller;

}
