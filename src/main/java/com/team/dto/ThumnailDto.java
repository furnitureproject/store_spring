package com.team.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ThumnailDto {

    private Long productCode;

    private String productTitle;

    private String productDesc;

    private Long productHit;

    private Long category3Code;

    private byte[] thumImgData;

    private String thumImgType;

}
