package com.team.service;

import com.team.entity.Delivery;

import org.springframework.stereotype.Service;

@Service
public interface DeliveryService {
    
    //delivery 등록
    public void insertDelivery(Delivery delivery);
}
