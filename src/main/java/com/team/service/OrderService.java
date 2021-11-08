package com.team.service;

import java.util.List;

import com.team.entity.Order;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    // 주문 추가
    public int insertListOrder(List<Order> list);

    // 주문 추가
    public int insertOrder(Order order);

    // 주문 수정
    public int updateOrder(Order order);

    // 주문 삭제
    public int deleteOrder(Long orderno);

    // 주문 하나 찾기
    public Order selectOrderOne(Long orderno);

    // 주문 찾기(유저)
    public List<Order> selectOrderUser(String userid);

}
