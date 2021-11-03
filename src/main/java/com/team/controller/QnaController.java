package com.team.controller;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.QnA;
import com.team.entity.User;
import com.team.jwt.JwtUtil;
import com.team.service.ProductService;
import com.team.service.QnaService;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QnaController {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    QnaService qService;

    @Autowired
    UserService uService;

    @Autowired
    ProductService pService;

    // qna 등록
    // 127.0.0.1:8080/ROOT/qna/insert?no=14
    // 여기서 넘어오는 no는 물품 정보
    @RequestMapping(value = "/qna/insert", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productInsertPOST(@RequestBody QnA qnA,
            @RequestParam(name = "no", defaultValue = "0") long no,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
        try {
            User user = uService.selectUserOne(userid);
            if (jwtUtil.extractUsername(token).equals(userid)) {
                qnA.setUser(user); // 회원정보를 통해 member를 찾아줌
                //qnA.setProduct(pService.selectProduct(no)); // 파라미터로 넘어온 물품코드를 통해 물품정보 찾기
                //qService.insertQna(qnA);
                map.put("result", 1L);
            }
            map.put("result", 0L);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
}
