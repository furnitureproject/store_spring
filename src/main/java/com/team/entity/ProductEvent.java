package com.team.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "PRODUCT_EVENT")
public class ProductEvent {
    
    // 이벤트 코드
    @Id
    @Column(name = "EVENT_CODE")
    private Long eventCode;

    // 이벤트 이름
    @Column(name = "EVENT_NAME")
    private String eventName;

    // 등록일자
    @CreationTimestamp
    @Column(name = "EVENT_REGDATE", updatable = false)
    private Date eventRegdate;

    // 할인율 1-100까지
    @Column(name = "EVENT_DISCOUNT")
    private int eventDiscount;
}
