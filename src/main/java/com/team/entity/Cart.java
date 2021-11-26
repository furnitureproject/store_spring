package com.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@SequenceGenerator(name = "SEQ_CART_NUM", sequenceName = "SEQ_CART_NUM", initialValue = 1, allocationSize = 1)
public class Cart {

    // 카트 순서용 넘버링
    @Id
    @Column(name = "CART_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CART_NUM")
    private Long cartNo;

    // 고른거 수량
    @Column(name = "CART_QUANTITY")
    private Long cartOptionCount;

    @Column(name = "CART_STATUS")
    private int cartStatus;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "OPTION_CODE")
    private ProductOption productOption;

}
