package com.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
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
@SequenceGenerator(name = "SEQ_OPIMG_NUM", sequenceName = "SEQ_OPIMG_NUM", initialValue = 1, allocationSize = 1)
@Table(name = "OPTION_IMG")
public class OptionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OPIMG_NUM")
    @Column(name = "OPTION_IMG_NUM")
    private Long optionImgNum;

    @Column(name = "OPTION_IMG_NAME")
    private String optionImgName;

    @Lob
    @Column(name = "OPTION_IMG_DATA")
    private byte[] optionImgdata;

    @Column(name = "OPTION_IMG_SIZE")
    private Long optionImgSize;

    @Column(name = "OPTION_IMG_TYPE")
    private String optionImgType;

    @ManyToOne
    @JoinColumn(name = "OPTION_CODE")
    private ProductOption productOption;
}
