package com.team.repository;

import java.util.List;

import com.team.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCart_User_UserIdOrderByOrderDateDesc(String userid);
}
