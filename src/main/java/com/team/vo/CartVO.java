package com.team.vo;

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
public class CartVO {

    private Long cartNo;

    private String cartImgName;

    private Long cartCode;

    private Long cartOptionCount;

    private Long cartOptionPrice;

    private int cartStatus;

    private String user;

    private String cartOptionName;

    private String cartName;
}
