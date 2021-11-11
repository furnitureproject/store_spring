package com.team.entity;

// import org.springframework.beans.factory.annotation.Value;

public interface ProductProjection {

    Long getproductCode();

    String getproductTitle();

    String getproductDesc();

    Integer getproductHit();

    Long getcategory_categoryCode();

    // @Value("#{target.category}")
    // Category getcategory();

}
