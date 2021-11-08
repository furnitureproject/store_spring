package com.team.service;

import com.team.entity.Delivery;
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
    
}
