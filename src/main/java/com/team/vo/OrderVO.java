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
public class OrderVO {

    private Long optionCode;

    private int cartOptionCount;

    private Long optionPrice;

}
