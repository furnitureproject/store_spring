package com.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@SequenceGenerator(name = "SEQ_DEL_NUM", sequenceName = "SEQ_DEL_NUM", initialValue = 1, allocationSize = 1)
@Table(name = "DELIVERY")
public class Delivery {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEL_NUM")
    @Column(name = "DELIVERY_NO")
    private Long deliveryNo;

    @Column(name = "DELIVERY_CODE")
    private Long deliveryCode=0L;

    @ManyToOne
    @JoinColumn(name = "ORDER_NO", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PAY_NO", nullable = false)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "USER_ADDRESS", nullable = false)
    private UserAddress userAddress;

    @ManyToOne
    @JoinColumn(name = "SELLER", nullable = false)
    private Seller seller;

}
