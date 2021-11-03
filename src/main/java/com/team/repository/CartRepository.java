package com.team.repository;

import java.util.List;

import com.team.entity.Cart;
import com.team.entity.CartProjection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<CartProjection> findByUser_UserIdOrderByCartNoDesc(String userId);

}
