package com.team.service;

import java.util.List;
import java.util.Map;

import com.team.entity.Order;
import com.team.entity.OrderProjection;
import com.team.vo.OrderVO;

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

    // 모든 주문 찾기
    public List<Order> selectAllOrder();

    // 프로젝션된 주문 하나 찾기
    public OrderProjection selectOrderProjectionOne(Long orderno);

    // 주문 찾기(유저)
    public List<OrderProjection> selectOrderUser(String userid);

    // 유저에 따른 주문(같은 옵션인 경우 수량 그룹화로 통합)
    public List<OrderVO> selectQueryUserOrder(Map<String, Object> map);

    // cartno에 따른 order 찾기
    public Order selectOrderForCartNo(Long cartNo);

    // ordercode에 따른 order 찾기
    public List<OrderProjection> selectOrderForOrderCode(Long orderCode);

    // ordercode
    public Long nextCode();

}
