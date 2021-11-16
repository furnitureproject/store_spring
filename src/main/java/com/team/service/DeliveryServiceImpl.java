package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.Delivery;
import com.team.entity.DeliveryProjection;
import com.team.repository.DeliveryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService{
    
    @Autowired
    DeliveryRepository dRepository;

    //delivery 등록
    @Override
    public void insertDelivery(Delivery delivery) {
        dRepository.save(delivery);        
    }

    //delivery 수정(seller)
    @Override
    public void updateDelivery(Delivery delivery) {
        dRepository.save(delivery);
    }

    //delivery 정보 가져오기
    @Override
    public Delivery selectDelivery(long no) {
        Optional<Delivery> delivery = dRepository.findById(no);
        return delivery.orElse(null);
    }

    //delivery 삭제
    @Override
    public void deleteDelivery(long no) {
        dRepository.deleteById(no);
    }

    //delivery 조회
    @Override
    public List<DeliveryProjection> selectUseridDelivery(String userid) {
        return dRepository.findByUserinput_Order_Cart_User_UserIdOrderByDeliveryNo(userid);
    }

    //sellerid 별 delivery 정보 조회
    @Override
    public List<Delivery> selectSelleridDelivery(String sellerid) {
        return dRepository.findByUserinput_Order_Cart_ProductOption_Product_Seller_SellerIdOrderByDeliveryNo(sellerid);
    }
    
    
}
