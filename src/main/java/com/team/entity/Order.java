package com.team.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Order {

   @Id
   @Column(name = "ORDER_NO")
   private Long orderNo;

   @Column(name = "ORDER_STATUS")
   private int orderState = 1;

   @CreationTimestamp
   @Column(name = "ORDER_DATE")
   private Date orderDate;

   @ManyToOne
   @JoinColumn(name = "CART_NO")
   private Cart cart;

}
