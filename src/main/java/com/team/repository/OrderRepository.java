package com.team.repository;

import java.util.List;

import com.team.entity.Order;
import com.team.entity.OrderProjection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<OrderProjection> findByCart_User_UserIdOrderByOrderDateDesc(String userid);

    OrderProjection findByOrderNo(Long orderNo);
}
