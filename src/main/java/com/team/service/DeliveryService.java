package com.team.service;

import java.util.List;
import java.util.Map;

import com.team.entity.Delivery;
import com.team.entity.DeliveryProjection;
import com.team.vo.DeliveryVO;

import org.springframework.stereotype.Service;

@Service
public interface DeliveryService {
    
    //delivery 등록
    public void insertDelivery(Delivery delivery);

    public Delivery insertDelivery2(Delivery delivery);

    //일괄 추가1
    public void insertAllDelivery(List<Delivery> list);

    //delivery 수정(seller)
    public void updateDelivery(Delivery delivery);

    //delivery 정보 가져오기
    public Delivery selectDelivery(long no);

    //delivery 삭제
    public void deleteDelivery(long no);

    //deliveryCode 1개 조회
    public Delivery selectDelOne(Long no);

    //userid 별 delivery 정보 조회
    public List<DeliveryProjection> selectUseridDelivery(String userid);

    //sellerid 별 delivery 정보 조회
    public List<DeliveryProjection> selectSelleridDelivery(String sellerid);

    // 유저 별 delivery 조회
    public List<DeliveryVO> selectUserDelList(Map<String, Object> map);

    // delivery 총 개수 조회(userid 기준)
    long countByUseridDelivery(String id);
}
