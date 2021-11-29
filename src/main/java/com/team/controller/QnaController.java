package com.team.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.team.vo.QnAVO;

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

    int PAGECNT = 10;

    // qna 등록
    // 127.0.0.1:8080/ROOT/qna/insert?pno=
    // {"qnaTitle":"문의","qnaContent":"내용"}
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
                map.put("result", 200);
            }
            else{
                map.put("result", 400);
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
                map.put("result", 200);
            }
            else{
                map.put("result", 400);
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
            String sellerid = qService.selectQna(no).getProduct().getSeller().getSellerId(); //qna의 물품을 등록한 sellerid 찾기
            System.out.println(sellerid);
            if(tsellerid.equals(sellerid)){
                QnA qnA2 = qService.selectQna(no);
                qnA2.setQnaReply(qnA.getQnaReply());
                qnA2.setQnaReplyCheck(1);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                //System.out.println(timestamp);
                qnA2.setQnaReplyRegdate(timestamp);
                qService.updateQna(qnA2);
                map.put("result", 200);
            }
            else{
                map.put("result", 400);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // qna 삭제
    // 127.0.0.1:8080/ROOT/qna/delete?qnano=
    @RequestMapping(value = "/qna/delete", method = {
        RequestMethod.DELETE }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productDelete(@RequestBody QnA qnA,
        @RequestParam(name = "qnano", defaultValue = "0") long no, 
        @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            String quserid = qService.selectQna(no).getUser().getUserId(); //qna 정보에서 userid 찾기
            if(userid.equals(quserid)){
                //System.out.println(qnA.getQnaNum());
                qService.deleteQna(qnA.getQnaNum());
                map.put("result", 200);
            }
            else{
                map.put("result", 400);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //qna 1개 조회
    // 127.0.0.1:8080/ROOT/qna/select?qnano= 
    @RequestMapping(value = "/qna/select", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> qnaOneGET(
        @RequestParam(name = "qnano", defaultValue = "0") long no) {
        Map<String, Object> map = new HashMap<>();
        try {            
            QnAProjection qnA = qService.selectQnaOne(no);
            map.put("qna", qnA);
            map.put("result", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 제품 코드 별 qna 조회(sql)
    // 127.0.0.1:8080/ROOT/select_qnalist?code= 물품 코드&page=
    @RequestMapping(value = "/select_qnalist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectPcodeQnaGET(
    @RequestParam("code") Long code,
    @RequestParam(value = "page", defaultValue = "1")int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            long cnt = qService.countByPcodeQna(code); //제품코드 별 qna 갯수
            Map<String, Object> map1 = new HashMap<>();
            int rpage1 = page * PAGECNT;
            int rpage = rpage1 - (PAGECNT - 1);
            
            map1.put("page", rpage);
            map1.put("page1", rpage1);
            map1.put("code", code);
            List<QnAVO> list = qService.selectPcodeQnaList(map1);
            map.put("cnt", (cnt - 1) / 10 + 1); //페이지 수
            map.put("list", list);
            map.put("result", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 회원id 별 qna 조회
    // 127.0.0.1:8080/ROOT/select_userqnalist?page=
    @RequestMapping(value = "/select_userqnalist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectUidQnaGET(
    @RequestHeader("token") String token,
    @RequestParam(value = "page", defaultValue = "1")int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            String sellerid = jwtUtil.extractUsername(token); // token을 통해 판매자 정보 찾기
            long cnt = qService.countByUseridQna(sellerid); //userid 별 qna 갯수
            Map<String, Object> map1 = new HashMap<>();
            int rpage1 = page * PAGECNT;
            int rpage = rpage1 - (PAGECNT - 1);
            
            map1.put("page", rpage);
            map1.put("page1", rpage1);
            map1.put("id", sellerid);
            List<QnAVO> list = qService.selectUserQnaList(map1);
            map.put("cnt", (cnt - 1) / 10 + 1); //페이지 수
            map.put("list", list);
            map.put("result", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
}
