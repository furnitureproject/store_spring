package com.team.entity;

import java.util.Date;

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

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 구매자는 구매했을 경우에만 리뷰를 등록가능(아직 안해도됨)
// 이미지는 반드시 첨부하지는 않아도 됨

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@SequenceGenerator(name = "SEQ_REVIEW_NUM", sequenceName = "SEQ_REVIEW_NUM", initialValue = 1, allocationSize = 1)
@Table(name = "REVIEW")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVIEW_NUM")
    @Column(name = "REVIEW_NUM")
    private Long reviewNum;

    // 제목
    @Column(name = "REVIEW_TITLE", nullable = false)
    private String reviewTitle;

    // 내용
    @Column(name = "REVIEW_CONTENT", nullable = false)
    private String reviewcontent;

    // 별점 최대 5
    @Column(name = "REVIEW_STAR", nullable = false)
    private int reviewStar;

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

    // 등록일자
    @CreationTimestamp
    @Column(name = "REVIEW_REG_DATE", updatable = false)
    private Date reviewRegDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_CODE")
    private Product product;
}
