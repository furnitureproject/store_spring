package com.team.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Product;
import com.team.entity.QnA;
import com.team.entity.QnAProjection;
import com.team.entity.User;
import com.team.jwt.JwtUtil;
import com.team.service.ProductService;
import com.team.service.QnaService;
import com.team.service.SellerService;
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

    @Autowired
    SellerService sService;

    // qna 등록
    // 127.0.0.1:8080/ROOT/qna/insert?pno=
    // {"qnaTitle":"문의","qnaContent":"내용","user":{"userId":"user"},"product":{"productCode":1}}
    @RequestMapping(value = "/qna/insert", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> qnaInsertPOST(@RequestBody QnA qnA,
            @RequestParam(name = "pno", defaultValue = "0") long productCode,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            User user = uService.selectUserOne(userid); //user 정보 찾기
            Product product = pService.selectProductOne(productCode); //product 정보 찾기
            if(userid.equals(user.getUserId())){
                qnA.setUser(user); //userid
                qnA.setProduct(product);  //productcode
                qService.insertQna(qnA);
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

    // qna 수정(user)
    // 127.0.0.1:8080/ROOT/qna/update?qnano=1
    @RequestMapping(value = "/qna/update", method = {
        RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> qnaUpdatePUT(@RequestBody QnA qnA,
            @RequestParam(name = "qnano", defaultValue = "0") long no,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            String quserid = qService.selectQna(no).getUser().getUserId(); //qna 정보에서 userid 찾기
            if (userid.equals(quserid)) {   // 회원정보를 통해 user 찾기
                QnA qnA2 = qService.selectQna(no);
                qnA2.setQnaTitle(qnA.getQnaTitle());
                qnA2.setQnaContent(qnA.getQnaContent());
                qService.updateQna(qnA2);
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

    // qna 수정(seller)
    // 127.0.0.1:8080/ROOT/qna/update2?qnano=
    @RequestMapping(value = "/qna/update2", method = {
        RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> qnaUpdate2PUT(@RequestBody QnA qnA,
            @RequestParam(name = "qnano", defaultValue = "0") long no,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        String tsellerid = jwtUtil.extractUsername(token); // token을 통해 판매자 찾기
        try {
            String sellerid = qService.selectQna(no).getProduct().getSeller().getSellerId();
            if(tsellerid.equals(sellerid)){
                QnA qnA2 = qService.selectQna(no);
                qnA2.setQnaReply(qnA.getQnaReply());
                qnA2.setQnaReplyCheck(1);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                //System.out.println(timestamp);
                qnA2.setQnaReplyRegdate(timestamp);
                qService.updateQna(qnA2);
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

    // 제품 코드 별 제품 조회(sql)
    // 127.0.0.1:8080/ROOT/select_qnalist?code= 물품 코드
    @RequestMapping(value = "/select_qnalist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectPcodeQnaGET(
    @RequestParam("code") Long code) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<QnAProjection> list = qService.selectQnaList(code);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 회원id 별 qna 조회
    // 127.0.0.1:8080/ROOT/select_userqnalist?id= userid
    @RequestMapping(value = "/select_userqnalist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectPcodeQnaGET(
    @RequestParam("id") String userid) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<QnAProjection> list = qService.selectUserQnaList(userid);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

}
