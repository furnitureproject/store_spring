package com.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DELIVERY")
public class Delivery {
    
    @Id
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
