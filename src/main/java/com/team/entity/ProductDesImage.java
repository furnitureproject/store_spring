package com.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "PRODUCT_DES_IMG")
public class ProductDesImage {
    
    @Id
    @Column(name = "DES_IMG_NUM")
    private Long desImgNum;

    @Column(name = "DES_IMG_NAME")
    private String desImgName;

    @Lob
    @Column(name = "DES_IMG_DATA")
    private byte[] desImgData;

    @Column(name = "DES_IMG_SIZE")
    private Long desImgSize;

    @Column(name = "DES_IMG_TYPE")
    private String desImgType;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_CODE")
    private Product product;
}
