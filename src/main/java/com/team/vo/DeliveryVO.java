package com.team.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryVO {

    private long deliveryNo;

    private Long deliveryCode;

    private Long addressNo;

    private Date orderDate;
    private String orderDateString;

    private Long orderCode;

    private String userName;

    private String productTitle;

    private Long productCode;

    private String sellerId;

    private Long cartOptionCount;

    private String optionName;

    private String sellerName;

    private String receiverName;

    private Long price;
}
