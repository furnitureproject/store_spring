package com.team.controller;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Order;
import com.team.entity.UserInput;
import com.team.entity.UserInputProjection;
import com.team.jwt.JwtUtil;
import com.team.service.CartService;
import com.team.service.OrderService;
import com.team.service.ProductService;
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

    @Autowired
    OrderService oService;

    //userinput 등록
    //127.0.0.1:8080/ROOT/userinput/insert?ono=
    @RequestMapping(value = "/insert", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userinputInsertPOST(@RequestBody UserInput userInput,
            @RequestParam("ono")long no,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        //System.out.println(userInput.toString());
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            Order orderno = oService.selectOrderOne(no);    //order no 가져오기
            String orderid = oService.selectOrderOne(no).getCart().getUser().getUserId(); //order 정보를 통해 userid 찾기
            String ordername = oService.selectOrderOne(no).getCart().getUser().getUserName(); //order 정보를 통해 username 찾기
            if(userid.equals(orderid)){
                userInput.setUinputName(ordername); //주문자 이름
                userInput.setOrder(orderno); //order no
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

    // userinput 삭제
    // 127.0.0.1:8080/ROOT/userinput/delete?ono=&uno=
    @RequestMapping(value = "/delete", method = {
        RequestMethod.DELETE }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productDelete(@RequestBody UserInput userInput,
        @RequestParam(name = "ono", defaultValue = "0") long ono,
        @RequestParam(name = "uno", defaultValue = "0") long no, 
        @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            String orderid = oService.selectOrderOne(ono).getCart().getUser().getUserId(); //order 정보를 통해 userid 찾기
            if(userid.equals(orderid)){
                System.out.println(userInput.getUinputno());
                uiService.deleteUserinput(no);
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

    // userinput 수정
    // 127.0.0.1:8080/ROOT/userinput/update?ono=&uno=
    @RequestMapping(value = "/update", method = {
        RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> qnaUpdatePUT(@RequestBody UserInput userInput,
            @RequestParam(name = "ono", defaultValue = "0") long ono,
            @RequestParam(name = "uno", defaultValue = "0") long no,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            String orderid = oService.selectOrderOne(ono).getCart().getUser().getUserId(); //order 정보를 통해 userid 찾기
            if (userid.equals(orderid)) {   // 회원정보를 통해 user 찾기
                UserInput userInput2 = uiService.selectUserInput(no);
                userInput2.setUinputZipCode(userInput.getUinputZipCode());
                userInput2.setUinputAddress(userInput.getUinputAddress());
                userInput2.setUinputAddDetail(userInput.getUinputAddDetail());
                userInput2.setUinputName(userInput.getUinputName());
                userInput2.setUinputRequirement(userInput.getUinputRequirement());
                uiService.updateUserInput(userInput2);
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

    //userinput 조회
    // 127.0.0.1:8080/ROOT/userinput/select?ono=
    @RequestMapping(value = "/select", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> uinputOneGET(
        @RequestParam(name = "ono", defaultValue = "0") long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            UserInputProjection userInput = uiService.selectUserInput1(no);
            //System.out.println(userInput.toString());
            map.put("userinput", userInput);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
}
