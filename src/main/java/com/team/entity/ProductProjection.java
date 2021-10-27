package com.team.entity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface ProductProjection {

    Long getproduct_Code();

    String getProduct_Title();

    Long getoption_Price();

    int getrown();

}
