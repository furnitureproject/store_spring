package com.team.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Cart;
import com.team.entity.CartProjection;
import com.team.entity.ProductOption;
import com.team.entity.User;
import com.team.enums.OrderStatus;
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
    public Map<String, Object> selectCart(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            List<CartProjection> list1 = new ArrayList<>();
            List<CartProjection> list = cService.selectAllUserCart(userid);
            for (int i = 0; i < list.size(); i++) {
                CartProjection cart = list.get(i);
                Long number = cart.getProductOption_Product_ProductCode();
                if (cart.getCartStatus() == 0 || cart.getCartStatus() == 1) {
                    list1.add(cart);
                    map.put("image" + i, "/ROOT/product/select_image?productCode=" + number);
                }
            }
            map.put("status", 200);
            map.put("list", list1);

        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/cart_one", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCartOne(@RequestHeader("token") String token, @RequestParam("cartno") Long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            CartProjection cart = cService.selectCartProjectionOne(no);
            Long no1 = cService.selectCartOne(no).getProductOption().getProduct().getProductCode();
            if (cart.getUser_UserId().equals(userid)) {
                map.put("status", 200);
                map.put("cart", cart);
                map.put("image", "/ROOT/product/select_image?productCode=" + no1);
            } else {
                map.put("status", "적합한 권한을 가지고 있지 않습니다");
            }

        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/cart", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertCart(@RequestBody Cart[] cart, @RequestHeader("token") String token,
            @RequestParam("optioncode") Long[] code) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user = uService.selectUserOne(userid);
            for (int i = 0; i < code.length; i++) {
                if (code[i] != null) {
                    ProductOption productOption = pOService.selectProductOptionOne(code[i]);
                    cart[i].setUser(user);
                    cart[i].setProductOption(productOption);
                    cService.insertCart(cart[i]);
                }
            }
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
                map.put("status", "적합한 권한을 가지고 있지 않습니다");
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

    @GetMapping(value = "/cart1")
    public Map<String, Object> Cart() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", OrderStatus.COMPLETE.getCode());
        map.put("statis", OrderStatus.COMPLETE.getStatus());
        return map;
    }

}
