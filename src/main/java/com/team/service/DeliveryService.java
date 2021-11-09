package com.team.service;

import com.team.entity.Delivery;

import org.springframework.stereotype.Service;

@Service
public interface DeliveryService {
    
    //delivery 등록
    public void insertDelivery(Delivery delivery);

    //delivery 수정(seller)
    public void updateDelivery(Delivery delivery);

    //delivery 정보 가져오기
    public Delivery selectDelivery(long no);

    //delivery 삭제
    public void deleteDelivery(long no);
}
