package com.team.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.team.entity.Order;
import com.team.entity.OrderProjection;
import com.team.repository.OrderRepository;
import com.team.vo.OrderVO;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository oRepository;

    @Autowired
    SqlSessionFactory sqlFactory;

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
    public List<Order> selectAllOrder() {
        return oRepository.findAll();
    }

    @Override
    public List<OrderProjection> selectOrderUser(String userid) {

        return oRepository.findByCart_User_UserIdOrderByOrderDateDesc(userid);
    }

    @Override
    public OrderProjection selectOrderProjectionOne(Long orderno) {

        return oRepository.findByOrderNo(orderno);
    }

    @Override
    public List<OrderVO> selectQueryUserOrder(Map<String, Object> map) {

        return sqlFactory.openSession().selectList("select_user_productoption_list", map);
        // return sqlFactory.openSession().selectOne("select_user_productoption_list",
        // map);
    }

    @Override
    public Order selectOrderForCartNo(Long cartNo) {
        return oRepository.findByCart_CartNo(cartNo);
    }

    @Override
    public Long nextCode() {
        Long seq = oRepository.getNextSeqVal();

        String newseq = String.format("%08d", seq);

        Date time = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        String today = format.format(time);

        Long code = Long.parseLong(today + newseq);
        return code;
    }

    @Override
    public List<OrderProjection> selectOrderForOrderCode(Long orderCode) {

        return oRepository.findByOrderCode(orderCode);
    }

}
