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

    private String sellerId;

    private Long addressNo;
    
    private Long paymentNo;

    private Long orderNo;

    private Date orderDate;
    
}
