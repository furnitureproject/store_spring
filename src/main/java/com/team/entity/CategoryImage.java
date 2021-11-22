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
@Table(name = "CATEGORY_IMG")
@SequenceGenerator(name = "SEQ_CATIMG_NUM", sequenceName = "SEQ_CATIMG_NUM", initialValue = 1, allocationSize = 1)
public class CategoryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CATIMG_NUM")
    @Column(name = "CATE_IMG_NUM")
    private Long cateImgNum;

    @Column(name = "CATE_IMG_NAME")
    private String cateImgName;

    @Lob
    @Column(name = "CATE_IMG_DATA")
    private byte[] cateImgData;

    @Column(name = "CATE_IMG_SIZE")
    private Long cateImgSize;

    @Column(name = "CATE_IMG_TYPE")
    private String cateImgType;

    @ManyToOne
    @JoinColumn(name = "CATE_CODE")
    private Category category;

}
