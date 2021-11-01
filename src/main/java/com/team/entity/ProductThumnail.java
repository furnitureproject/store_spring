package com.team.entity;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.team.dto.ThumnailDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@SequenceGenerator(name = "SEQ_THUM_NUM", sequenceName = "SEQ_THUM_NUM", initialValue = 1, allocationSize = 1)
@Table(name = "PRODUCT_THUMNAIL")

@NamedNativeQuery(name = "thumnail_projection_image_dto", query = "SELECT PRODUCT.PRODUCT_CODE, PRODUCT.PRODUCT_TITLE,"
                + " PRODUCT.PRODUCT_HIT, PRODUCT.CATEGORY3_CODE, PRODUCT.PRODUCT_DESC, "
                + " PRODUCT_THUMNAIL.THUM_IMG_DATA, PRODUCT_THUMNAIL.THUM_IMG_TYPE FROM PRODUCT,PRODUCT_THUMNAIL "
                + " WHERE PRODUCT.PRODUCT_CODE=PRODUCT_THUMNAIL.PRODUCT_CODE AND CATEGORY3_CODE=:code"
                + " AND PRODUCT_TITLE LIKE '%' || :title || '%' ORDER BY PRODUCT_CODE DESC", resultSetMapping = "resultmap_thumnail_projection_image_dto")
@SqlResultSetMapping(name = "resultmap_thumnail_projection_image_dto", classes = @ConstructorResult(targetClass = ThumnailDto.class, columns = {
                @ColumnResult(name = "PRODUCT_CODE", type = Long.class),
                @ColumnResult(name = "PRODUCT_TITLE", type = String.class),
                @ColumnResult(name = "PRODUCT_DESC", type = String.class),
                @ColumnResult(name = "PRODUCT_HIT", type = Long.class),
                @ColumnResult(name = "CATEGORY3_CODE", type = Long.class),
                @ColumnResult(name = "THUM_IMG_DATA", type = byte[].class),
                @ColumnResult(name = "THUM_IMG_TYPE", type = String.class) }))
public class ProductThumnail {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_THUM_NUM")
        @Column(name = "THUM_IMG_NUM")
        private Long thumImgNum;

        @Column(name = "THUM_IMG_NAME")
        private String thumImgName;

        @Lob
        @Column(name = "THUM_IMG_DATA")
        private byte[] thumImgData;

        @Column(name = "THUM_IMG_SIZE")
        private Long thumImgSize;

        @Column(name = "THUM_IMG_TYPE")
        private String thumImgType;

        @ManyToOne
        @JoinColumn(name = "PRODUCT_CODE")
        private Product product;

}
