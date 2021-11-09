package com.team.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Order;
import com.team.entity.User;
import com.team.jwt.JwtUtil;
import com.team.service.CartService;
import com.team.service.OrderService;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class OrderController {

    @Autowired
    UserService uService;

    @Autowired
    OrderService oService;

    @Autowired
    CartService cService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping(value = "/order")
    public Map<String, Object> userOrderListGet(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);

            map.put("status", 200);
            map.put("list", oService.selectOrderUser(userid));
            map.put("image", "127.0.0.1:8080/ROOT/product/select_image?productCode={productCode}");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/order_one")
    public Map<String, Object> oneOrderGet(@RequestParam("orderno") Long no, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user = uService.selectUserOne(userid);
            Long proimg = oService.selectOrderOne(no).getCart().getProductOption().getProduct().getProductCode();
            if (oService.selectOrderOne(no).getCart().getUser().equals(user)) {
                map.put("status", 200);
                map.put("order", oService.selectOrderProjectionOne(no));
                map.put("image", "127.0.0.1:8080/ROOT/product/select_image?productCode=" + proimg);
            } else {
                map.put("status", "적합한 권한을 가지고 있지 않습니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/order")
    public Map<String, Object> userOrderPost(@RequestBody Order[] order, @RequestHeader("token") String token,
            @RequestParam("cartno") Long[] no) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            for (int i = 0; i < order.length; i++) {
                order[i].setCart(cService.selectCartOne(no[i]));
                if (order[i].getCart().getUser().getUserId().equals(userid)) {
                    oService.insertOrder(order[i]);
                    map.put("status", 200);
                } else {
                    map.put("status", "적합한 권한을 가지고 있지 않습니다");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/order")
    public Map<String, Object> updateOrderPut(@RequestHeader("token") String token, @RequestBody Order order) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            Order order1 = oService.selectOrderOne(order.getOrderNo());
            if (order1.getCart().getUser().getUserId().equals(userid)) {
                oService.updateOrder(order);
                map.put("status", 200);
            } else {
                map.put("status", "적합한 권한을 가지고 있지 않습니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/order")
    public Map<String, Object> orderDelete(@RequestHeader("token") String token, @RequestParam("orderno") Long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user = uService.selectUserOne(userid);
            if (oService.selectOrderOne(no).getCart().getUser().equals(user)) {
                oService.deleteOrder(no);
                map.put("status", 200);
            }
            map.put("status", "적합한 권한을 가지고 있지 않습니다");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
}
