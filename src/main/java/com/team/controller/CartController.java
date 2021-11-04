package com.team.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Cart;
import com.team.entity.ProductOption;
import com.team.entity.User;
import com.team.jwt.JwtUtil;
import com.team.service.CartService;
import com.team.service.ProductOptionService;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CartController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CartService cService;

    @Autowired
    ProductOptionService pOService;

    @Autowired
    UserService uService;

    @GetMapping(value = "/cart", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCart(
    // @RequestHeader("token") String token
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            // String userid = jwtUtil.extractUsername(token);
            User user = uService.selectUserOne("s");
            String userid = user.getUserId();

            map.put("status", 200);
            map.put("obj", cService.selectAllUserCart(userid));

        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/cart", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertCart(@RequestBody Cart cart,
            // @RequestHeader("token") String token,
            @RequestParam("optioncode") Long code) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user1 = uService.selectUserOne("s");
            // String userid = jwtUtil.extractUsername(token);
            String userid = user1.getUserId();
            User user = uService.selectUserOne(userid);
            ProductOption productOption = pOService.selectProductOptionOne(code);
            cart.setUser(user);
            cart.setProductOption(productOption);
            cService.insertCart(cart);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/cart", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateCart(@RequestBody Cart cart, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            Cart cart1 = cService.selectCartOne(cart.getCartCode());
            if (cart1.getUser().getUserId().equals(userid)) {
                cart1.setCartOptionCount(cart.getCartOptionCount());
                // cart1.setProductOption(pOService.selectProductOptionOne(code));
                cService.updateCart(cart1);
                map.put("status", 200);
            } else {
                map.put("status", "잘못된 유저입니다");
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/cart", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteCart(@RequestBody Cart cart, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            if (cart.getUser().getUserId().equals(userid)) {
                cService.deleteCart(cart.getCartCode());
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

}
