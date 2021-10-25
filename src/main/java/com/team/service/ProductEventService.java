package com.team.service;

import java.util.List;

import com.team.entity.ProductEvent;

import org.springframework.stereotype.Service;

@Service
public interface ProductEventService {
    
    // 이벤트 등록
    // return 1
    public int insertProductEvent(ProductEvent productEvent);

    // 이벤트 수정
    // return 1
    public int updateProductEvent(ProductEvent productEvent);

    // 이벤트 삭제
    // return 1
    public int deleteProductEvent(Long eventCode);

    // 이벤트 1개 조회
    // return productEvent 혹은 null
    public ProductEvent selectProductEventOne(Long eventCode);

    // 이벤트 전체 조회
    // return List<ProductEvent>
    public List<ProductEvent> selectProductEventAll();
}
