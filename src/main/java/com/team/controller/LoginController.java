package com.team.controller;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Seller;
import com.team.entity.User;
import com.team.jwt.JwtUtil;
import com.team.service.SecurityUserDetailServiceimpl;
import com.team.service.SellerService;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    SellerService sellerService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SecurityUserDetailServiceimpl sserviceimpl;

    @Autowired
    UserService uService;

    @PostMapping(value = "/userlogin", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userloginPost(@RequestBody User user) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {

            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPw()));
            
            map.put("status", 200);
            map.put("token", jwtUtil.generateToken(user.getUserId()));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", e.hashCode());
        }

        return map;
    }

    @PostMapping(value = "/userinsert", consumes = MediaType.ALL_VALUE, produces
    = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userinsertTest(@RequestBody User user){
    Map<String, Object> map = new HashMap<>();
    try {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        user.setUserPw(bcpe.encode(user.getUserPw()));
        uService.insertUser(user);
        map.put("status", 200);
    }
        catch(Exception e){
        e.printStackTrace();
        map.put("status", e.hashCode());
    }
        return map;
    }

    @PostMapping(value = "/sellerlogin", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> sellerloginPost(@RequestBody Seller seller) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(seller.getSellerId(), seller.getSellerPw()));

            map.put("status", 200);
            map.put("token", jwtUtil.generateToken(seller.getSellerId()));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", e.hashCode());
        }

        return map;
    }

}
