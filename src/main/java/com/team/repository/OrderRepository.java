package com.team.repository;

import java.util.List;

import com.team.entity.Order;
import com.team.entity.OrderProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT SEQ_ORDER.nextval FROM DUAL", nativeQuery = true)
    Long getNextSeqVal();

    List<OrderProjection> findByCart_User_UserIdOrderByOrderDateDesc(String userid);

    OrderProjection findByOrderNo(Long orderNo);

    Order findByCart_CartNo(Long cartNo);

    List<OrderProjection> findByOrderCode(Long orderCode);

}
