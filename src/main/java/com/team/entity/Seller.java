package com.team.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "SELLER")
public class Seller {
    
    @Id
    @Column(name = "SELLER_ID")
    private String sellerId;
    private String role = "SELLER";

    @Column(name = "SELLER_PW")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String sellerPw;

    private String sellerNewPw;

    @Column(name = "SELLER_NAME")
    private String sellerName;

    @Column(name = "SELLER_BIRTH")
    private String sellerBirth;

    @Column(name = "SELLER_PHONE")
    private String sellerPhone;

    @Column(name = "SELLER_EMAIL")
    private String sellerEmail;

    @CreationTimestamp
    @Column(name = "SELLER_REGDATE", updatable = false)
    private Date sellerRegdate;

    @Column(name = "SELLER_EDITDATE")
    private Date sellerEditdate = null;

    @Column(name = "SELLER_DELETE_CHECK")
    private int sellerDeletecheck = 0;
 
    @Column(name = "BUSINESS_NAME")
    private String businessName;

    @Column(name = "BUSINESS_CODE")
    private Long businessCode;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "STORE_PHONE")
    private String storePhone;
}
