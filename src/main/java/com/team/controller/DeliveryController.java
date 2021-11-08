package com.team.controller;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Delivery;
import com.team.entity.User;
import com.team.entity.UserInput;
import com.team.jwt.JwtUtil;
import com.team.service.DeliveryService;
import com.team.service.UserService;
import com.team.service.UserinputService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/delivery")
public class DeliveryController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    DeliveryService dService;

    @Autowired
    UserService uService;

    @Autowired
    UserinputService uiSrvice;

    // qna 등록
    // 127.0.0.1:8080/ROOT/delivery/insert?uno=
    @RequestMapping(value = "/insert", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deliveryPOST(@RequestBody Delivery delivery,
            @RequestParam(name = "uno", defaultValue = "0") long no,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            User user = uService.selectUserOne(userid); //user 정보 찾기
            long userInput = uiSrvice.selectUserInput(no).getUinputno(); //userinput 정보 찾기
            if(userid.equals(user.getUserId())){
                
                
                map.put("result", 1L);
            }
            else{
                map.put("result", 0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
}
