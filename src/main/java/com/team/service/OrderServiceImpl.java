package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.Order;
import com.team.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository oRepository;

    @Override
    public int insertOrder(Order order) {
        oRepository.save(order);
        return 1;
    }

    @Override
    public int insertListOrder(List<Order> list) {
        oRepository.saveAll(list);
        return 1;
    }

    @Override
    public int updateOrder(Order order) {
        oRepository.save(order);
        return 1;
    }

    @Override
    public int deleteOrder(Long orderno) {
        oRepository.deleteById(orderno);
        return 1;
    }

    @Override
    public Order selectOrderOne(Long orderno) {
        Optional<Order> order = oRepository.findById(orderno);
        return order.orElse(null);
    }

    @Override
    public List<Order> selectOrderUser(String userid) {

        return oRepository.findByCart_User_UserIdOrderByOrderDateDesc(userid);
    }

}
