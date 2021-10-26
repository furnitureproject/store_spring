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
@Table(name = "USER_ADDRESS")
@SequenceGenerator(name = "SEQ_USER_ADDRESS_NO", sequenceName = "SEQ_USER_ADDRESS_NO", initialValue = 1, allocationSize = 1)
public class UserAddress {
    
    @Id
    @Column(name = "ADDRESS_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_ADDRESS_NO")
    private Long addressNo;

    @Column(name = "ADDRESS_ZIPCODE")
    private String addressZipcode;

    @Column(name = "ADDRESS_CITY")
    private String addressCity;

    @Column(name = "ADDRESS_DETAIL")
    private String addressDetail;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}