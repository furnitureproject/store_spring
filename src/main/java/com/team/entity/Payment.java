package com.team.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @Column(name = "PAYMENT_NO")
    private Long paymentNo;

    @Column(name = "PAYMENT_STATE")
    private int paymentStatus = 0;

    @CreationTimestamp
    @Column(name = "PAYMENT_REGDATE")
    private Date paymentRegdate;

}
