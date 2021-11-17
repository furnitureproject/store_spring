package com.team.repository;

import java.util.List;

import com.team.entity.Delivery;
import com.team.entity.DeliveryProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>{

    //deliveryCode 1개 조회
    @Query(value = "SELECT * FROM DELIVERY WHERE DELIVERY_NO=:no", nativeQuery = true)
    public Delivery queryDelOne(@Param("no") Long no);

    //userid 별 delivery 정보 조회
    List<DeliveryProjection> findByUserinput_Order_Cart_User_UserIdOrderByDeliveryNo(String userid);

    //sellerid 별 delivery 정보 조회
    List<DeliveryProjection> findByUserinput_Order_Cart_ProductOption_Product_Seller_SellerIdOrderByDeliveryNo(String sellerid);
}
