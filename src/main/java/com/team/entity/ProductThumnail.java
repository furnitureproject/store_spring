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
@Table(name = "PRODUCT_THUMNAIL")
public class ProductThumnail {

    @Id
    @Column(name = "THUM_IMG_NUM")
    private Long thumImgNum;

    @Column(name = "THUM_IMG_NAME")
    private String thumImgName;

    @Lob
    @Column(name = "THUM_IMG_DATA")
    private byte[] thumImgData;

    @Column(name = "THUM_IMG_SIZE")
    private Long thumImgSize;

    @Column(name = "THUM_IMG_TYPE")
    private String thumImgType;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_CODE")
    private Product product;

}
