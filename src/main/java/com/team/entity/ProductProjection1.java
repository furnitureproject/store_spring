package com.team.entity;

import org.springframework.beans.factory.annotation.Value;

public interface ProductProjection1 {

    Long getproduct_Code();

    String getProduct_Title();

    String getProduct_Desc();

    Long getProduct_Hit();

    Long getCategory3_Code();

    byte[] getThum_img_data();

    String getThum_img_type();

}
