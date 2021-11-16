package com.team.service;

import java.util.List;

import com.team.entity.Delivery;
import com.team.entity.DeliveryProjection;
import com.team.repository.DeliveryRepository;

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

    //userid 별 delivery 정보 조회
    public List<DeliveryProjection> selectUseridDelivery(String userid);

    //sellerid 별 delivery 정보 조회
    public List<Delivery> selectSelleridDelivery(String sellerid);
}
