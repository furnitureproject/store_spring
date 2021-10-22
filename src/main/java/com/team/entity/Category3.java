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
@Table(name = "CATEGORY3")
public class Category3 {
    
    @Id
    @Column(name = "CATEGORY3_CODE")
    private Long category3Code;

    @Column(name = "CATEGORY3_NAME")
    private String category3Name;

    @ManyToOne
    @JoinColumn(name = "CATEGORY2_CODE")
    private Category2 category2;
}
