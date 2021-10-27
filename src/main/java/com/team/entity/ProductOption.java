package com.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "PRODUCT_OPTION")
public class ProductOption {

    @Id
    @Column(name = "OPTION_CODE")
    private Long optionCode;

    @Column(name = "OPTION_NAME")
    private String optionName;

    @Column(name = "OPTION_QUANTITY")
    private int optionQuantity;

    @Column(name = "OPTION_PRICE")
    private Long optionPrice;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_CODE")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "EVENT_CODE")
    private ProductEvent productEvent;
}
