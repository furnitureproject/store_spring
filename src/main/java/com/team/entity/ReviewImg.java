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
@SequenceGenerator(name = "SEQ_REVIEW_IMG_NUM", sequenceName = "SEQ_REVIEW_IMG_NUM", initialValue = 1, allocationSize = 1)
@Table(name = "REVIEW_IMG")
public class ReviewImg {
    
    // 이미지 넘버링
    @Id
    @Column(name = "REVIEW_IMG_NUM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVIEW_IMG_NUM")
    private Long reviewImgNum;

    // 이미지 파일 이름
    @Column(name = "REVIEW_IMG_NAME", nullable = true)
    private String reviewImgName;

    // 이미지 파일 데이터
    @Lob
    @Column(name = "REVIEW_IMG_DATA", nullable = true)
    private byte[] reviewImgData;

    // 이미지 파일 사이즈
    @Column(name = "REVIEW_IMG_SIZE", nullable = true)
    private Long reviewImgSize;

    // 이미지 파일 타입
    @Column(name = "REVIEW_IMG_TYPE", nullable = true)
    private String reviewImgType;

    @ManyToOne
    @JoinColumn(name = "REVIEW_NUM")
    private Review review;

}
