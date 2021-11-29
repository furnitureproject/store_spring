package com.team.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@SequenceGenerator(name = "SEQ_QNA_NUM", sequenceName = "SEQ_QNA_NUM", initialValue = 1, allocationSize = 1)
@Table(name = "QNA_TB")
public class QnA {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QNA_NUM")
    @Column(name = "QNA_NUM")
    private Long qnaNum;

    @Column(name = "QNA_TITLE", nullable = false)
    private String qnaTitle;

    @Column(name = "QNA_CONTENT", nullable = false)
    private String qnaContent;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "QNA_REGDATE", updatable = false)
    private Date qnaRegdate;

    @Column(name = "QNA_REPLY")
    private String qnaReply = null;

    @Column(name = "QNA_REPLY_REGDATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qnaReplyRegdate = null;

    @Column(name = "QNA_REPLY_CHECK")
    private int qnaReplyCheck = 0;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_CODE", updatable = false)
    private Product product;


}
