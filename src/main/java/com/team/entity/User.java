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
@Table(name = "USER_TB")
public class User {

    @Id
    @Column(name = "USER_ID")
    private String userId;
    private String role = "USER";

    @Column(name = "USER_PW")
    private String userPw;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_BIRTH")
    private String userBirth;

    @Column(name = "USER_PHONE")
    private String userPhone;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "USER_POINT")
    private int userPoint = 0;

    @CreationTimestamp
    @Column(name = "USER_REGDATE", updatable = false)
    private Date userRegdate;

    @Column(name = "USER_EDITDATE")
    private Date userEditdate = null;

    @Column(name = "USER_DELETE_CHECK")
    private int userDeletecheck = 0;

    @Column(name = "USER_PWCHANGE_CHECK")
    private int pwChangeCheck = 0;
}
