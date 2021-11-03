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
@Table(name = "CATEGORY")
public class Category {

    @Id
    @Column(name = "CATE_CODE")
    private Long categoryCode;

    @Column(name = "CATE_NAME")
    private String categoryName;

    @Column(name = "CATE_TIER")
    private Integer categoryTier;

    @Column(name = "CATE_PARENT")
    private Long categoryParent;
}
