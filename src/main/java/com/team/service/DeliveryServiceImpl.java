package com.team.service;

import java.util.Optional;

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
    
    
}
