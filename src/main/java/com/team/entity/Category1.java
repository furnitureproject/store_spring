package com.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "CATEGORY1")
public class Category1 {
    @Id
    @Column(name = "CATEGORY1_CODE")
    private Long category1Code;

    @Column(name = "CATEGORY1_NAME")
    private String category1Name;
}
