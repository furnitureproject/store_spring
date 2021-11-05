package com.team.controller;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Product;
import com.team.entity.User;
import com.team.entity.UserInput;
import com.team.jwt.JwtUtil;
import com.team.service.CartService;
import com.team.service.ProductService;
import com.team.service.UserService;
import com.team.service.UserinputService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/userinput")
public class UserinputController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserinputService uiService;

    @Autowired
    UserService uService;

    @Autowired
    ProductService pService;

    @Autowired
    CartService cService;

    //order service autowired 하기!!

    //userinput 등록
    //127.0.0.1:8080/ROOT/userinput/insert
    @RequestMapping(value = "/insert", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> qnaInsertPOST(@RequestBody UserInput userInput,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            //cService.selectCartOne(cartNo);   //cart 정보 찾기
            //이후 order 정보 찾기로 cart정보 가져오기
            User user = uService.selectUserOne(userid); //user 정보 찾기
            //Product product = pService.selectProductOne(productCode); //product 정보 찾기
            if(userid.equals(user.getUserId())){
                // qnA.setUser(user); //userid
                // qnA.setProduct(product);  //productcode
                uiService.insertUserinput(userInput);
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
