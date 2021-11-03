package com.team.controller;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Product;
import com.team.entity.QnA;
import com.team.entity.User;
import com.team.jwt.JwtUtil;
import com.team.service.ProductService;
import com.team.service.QnaService;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    // 127.0.0.1:8080/ROOT/qna/insert?no=1
    // 여기서 넘어오는 no는 물품 정보
    // {"qnaTitle":"문의","qnaContent":"내용","user":{"userId":"user"},"product":{"productCode":1}}
    @RequestMapping(value = "/qna/insert", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> qnaInsertPOST(@RequestBody QnA qnA,
            @RequestParam(name = "no", defaultValue = "0") long no,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
        try {
            User user = uService.selectUserOne(userid);
            if (jwtUtil.extractUsername(token).equals(userid)) {// 회원정보를 통해 user 찾기
                qnA.setUser(user); //userid
                qnA.setProduct(pService.selectProductOne(no));  //productno
                qService.insertQna(qnA);
                map.put("result", 1L);
            }
            map.put("result", 0L);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //qna 수정
    //127.0.0.1:8080/ROOT/qna/update?no=1
    @RequestMapping(value = "/qna/update", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> qnaUpdatePOST(@RequestBody QnA qnA,
            @RequestParam(name = "no", defaultValue = "0") long no,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
        try {
            User user = uService.selectUserOne(userid);
            if (jwtUtil.extractUsername(token).equals(userid)) {// 회원정보를 통해 user 찾기
                QnA qnA2 = qService.selectQna(no);
                qnA2.setQnaTitle(qnA.getQnaTitle());
                qnA2.setQnaContent(qnA.getQnaContent());
                qnA2.setUser(user); //userid
                qnA2.setProduct(pService.selectProductOne(no));  //productno
                qService.insertQna(qnA2);
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
