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

@Entity
@SequenceGenerator(name = "SEQ_DEL_NUM", sequenceName = "SEQ_DEL_NUM", initialValue = 1, allocationSize = 1)
@Table(name = "DELIVERY")
public class Delivery {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEL_NUM")
    @Column(name = "DELIVERY_NO")
    private Long deliveryNo;

    @Column(name = "DELIVERY_CODE")
    private Long deliveryCode;

    @ManyToOne
    @JoinColumn(name = "UINPUT_NO")
    private UserInput userinput;

    @ManyToOne
    @JoinColumn(name = "PAY_NO")
    private Payment payment;


}