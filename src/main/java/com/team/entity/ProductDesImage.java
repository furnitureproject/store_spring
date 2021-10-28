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
@Table(name = "PRODUCT_DES_IMG")
@SequenceGenerator(name = "SEQ_IMG_NUM", sequenceName = "SEQ_IMG_NUM", initialValue = 1, allocationSize = 1)
public class ProductDesImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IMG_NUM")
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
