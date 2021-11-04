package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.Cart;
import com.team.entity.CartProjection;
import com.team.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cRepository;

    @Override
    public int insertCart(Cart cart) {
        cRepository.save(cart);
        return 1;
    }

    @Override
    public int updateCart(Cart cart) {
        cRepository.save(cart);
        return 1;
    }

    @Override
    public int deleteCart(Long cartNo) {
        cRepository.deleteById(cartNo);
        return 1;
    }

    @Override
    public Cart selectCartOne(Long cartNo) {
        Optional<Cart> cart = cRepository.findById(cartNo);
        return cart.orElse(null);
    }

    @Override
    public List<CartProjection> selectAllUserCart(String userId) {
        return cRepository.findByUser_UserIdOrderByCartNoDesc(userId);
    }

}