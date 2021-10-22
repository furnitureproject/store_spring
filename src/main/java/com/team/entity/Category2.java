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
@Table(name = "CATEGORY2")
public class Category2 {
    
    @Id
    @Column(name = "CATEGORY2_CODE")
    private Long category2Code;

    @Column(name = "CATEGORY2_NAME")
    private String category2Name;

    @ManyToOne
    @JoinColumn(name = "CATEGORY1_CODE")
    private Category1 category1;

}
