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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor

@Entity
@Table(name = "ORDER_TB")
@SequenceGenerator(name = "SEQ_ORDER_NO", sequenceName = "SEQ_ORDER_NO", initialValue = 1, allocationSize = 1)
public class Order {

   @Id
   @Column(name = "ORDER_NO")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDER_NO")
   private Long orderNo;

   @Column(name = "ORDER_STATUS")
   private int orderState = 1;

   @Column(name = "ORDER_CODE")
   private Long orderCode;

   @CreationTimestamp
   @Column(name = "ORDER_DATE")
   private Date orderDate;

   @ManyToOne
   @JoinColumn(name = "CART_NO")
   private Cart cart;

}
