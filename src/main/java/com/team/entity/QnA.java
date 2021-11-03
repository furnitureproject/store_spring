package com.team.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "QNA_TB")
public class QnA {
    
    @Id
    @Column(name = "QNA_NUM")
    private Long qnaNum;

    @Column(name = "QNA_TITLE", nullable = false)
    private String qnaTitle;

    @Column(name = "QNA_CONTENT", nullable = false)
    private String qnaContent;

    @CreationTimestamp
    @Column(name = "QNA_REGDATE", updatable = false)
    private Date qnaRegdate;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_CODE")
    private Product product;

}
