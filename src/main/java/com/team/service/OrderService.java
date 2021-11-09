package com.team.service;

import java.util.List;

import com.team.entity.Order;
import com.team.entity.OrderProjection;

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

    // 프로젝션된 주문 하나 찾기
    public OrderProjection selectOrderProjectionOne(Long orderno);

    // 주문 찾기(유저)
    public List<OrderProjection> selectOrderUser(String userid);

}
