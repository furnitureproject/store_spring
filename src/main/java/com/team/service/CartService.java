package com.team.service;

import java.util.List;

import com.team.entity.Cart;
import com.team.entity.CartProjection;

import org.springframework.stereotype.Service;

@Service
public interface CartService {

    // 카트 추가
    // return 1
    public int insertCart(Cart cart);

    // 카트 수정
    // return 1
    public int updateCart(Cart cart);

    // 카트 삭제
    // return 1
    public int deleteCart(Long cartNo);

    // 카트 1개 조회
    // return category
    public Cart selectCartOne(Long cartNo);

    // 카트 조회(유저에따른)
    // return List<Cart>
    public List<CartProjection> selectAllUserCart(String userId);

}
