package com.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
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
@SequenceGenerator(name = "SEQ_SUBIMG_NUM", sequenceName = "SEQ_SUBIMG_NUM", initialValue = 1, allocationSize = 1)
@Table(name = "PRODUCT_SUB_IMG")
public class ProductSubImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUBIMG_NUM")
    @Column(name = "SUB_IMG_NUM")
    private Long subImgNum;

    @Column(name = "SUB_IMG_NAME")
    private String subImgName;

    @Lob
    @Column(name = "SUB_IMG_DATA")
    private byte[] subImgdata;

    @Column(name = "SUB_IMG_SIZE")
    private Long subImgSize;

    @Column(name = "SUB_IMG_TYPE")
    private String subImgType;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_CODE")
    private Product product;

}
