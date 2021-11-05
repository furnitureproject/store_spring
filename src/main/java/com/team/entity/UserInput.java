package com.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "USER_INPUT")
public class UserInput {
    
    @Id
    @Column(name = "UINPUT_NO")
    private Long uInputNo;

    @Column(name = "UINPUT_ZIPCODE")
    private String uInputZipCode;

    @Column(name = "UINPUT_ADDRESS")
    private String uInputAddress;

    @Column(name = "UINPUT_ADDRESS_DETAIL")
    private String uInputAddDetail;

    @Column(name = "UINPUT_NAME")
    private String uInputName;

    @Column(name = "UINPUT_REQUIREMENT")
    private String uInputRequirement;

    @ManyToOne
    @JoinColumn(name = "ORDER_NO")
    private Order order;

}
