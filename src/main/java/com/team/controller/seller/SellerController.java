package com.team.controller.seller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Product;
import com.team.entity.Seller;
import com.team.jwt.JwtUtil;
import com.team.service.SellerService;
import com.team.vo.ProductVO;
import com.team.vo.QnAVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/seller")
public class SellerController {
    
    @Autowired
    SellerService sservice;

    @Autowired
    JwtUtil jwtUtil;

    int PAGECNT = 10;

    @GetMapping(value = "/test")
    public Map<String, Object> testttt(){

        Map<String, Object> map = new HashMap<>();
        // List<Seller> seller = sservice.selectSellerAll();
        // Assert.notNull(seller1, "데이터를 담아서 보내주세요!");
        Seller seller = new Seller();
        System.out.println(seller.getRole().toString());

        map.put("data", seller);
        map.put("res", ResponseEntity.ok(seller));
        // map.put("status", HttpStatus.OK);

        return map;
    }

    // 판매자 계정 생성 페이지
    @RequestMapping(value="/insert", method=RequestMethod.GET,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> registSellerGet() {

        Map<String, Object> map = new HashMap<>();
        try{
            map.put("200", "connect");
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }

        return map;
    }
    
    // 판매자 계정 생성
    @RequestMapping(value="/insert", method=RequestMethod.POST,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> registSellerPost(@RequestBody Seller seller) {

        Map<String, Object> map = new HashMap<>();
        try{
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            seller.setSellerPw(bcpe.encode(seller.getSellerPw()));
            sservice.insertSeller(seller);
            map.put("200", "connect");
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }

        return map;
    }

    // 판매자 정보 전달
    @RequestMapping(value = "/update", method = RequestMethod.GET,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSellerInfo(){
        Map<String, Object> map = new HashMap<>();
        try{
            // 토큰 생기면 수정할것 토큰에서 아이디 값 받아서 오는걸로 수정할것
            Seller seller = new Seller();
            String dbSellerPW = sservice.selectSellerOne(seller.getSellerId()).getSellerPw();
            String inputSellerPW = seller.getSellerPw();
            if(dbSellerPW == inputSellerPW){
                Seller seller2 = sservice.selectSellerOne(seller.getSellerId());
                map.put("seller", seller2);
                map.put("200", "connect");
            }
            else{
                map.put("warning", "wrong password");
            }
        }catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }
        return map;
    }

    // 판매자정보 수정
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> postSellerInfo(@RequestBody Seller seller){
        Map<String, Object> map = new HashMap<>();
        try{
            Seller oldSeller = sservice.selectSellerOne(seller.getSellerId());
            // if(seller.getSellerEmail() != null){
            //     oldSeller.setSellerEmail(seller.getSellerEmail());            
            // }
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            oldSeller.setSellerPw(bcpe.encode(seller.getSellerPw()));
            oldSeller.setSellerEmail(seller.getSellerEmail());    
            oldSeller.setSellerPhone(seller.getSellerPhone());
            oldSeller.setStorePhone(seller.getStorePhone());
            int updateCheck = sservice.updateSeller(oldSeller);
            if(updateCheck == 1){
                map.put("test", oldSeller.getSellerPw());
                map.put("status", 200);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }

        return map;
    }

    // 판매자 계정 삭제
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteSeller(Seller seller){
        Map<String, Object> map = new HashMap<>();
        // 토큰이랑 로그인 생기면 수정할것
        try{
            sservice.deleteSeller(seller);
            map.put("status", 200);

        }
        catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }
        return map;
    }

    // 판매자 계정 삭제 페이지
    @RequestMapping(value = "/delete", method = RequestMethod.GET,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> GetDeletePage(){
        Map<String, Object> map = new HashMap<>();
        try{
            // 토큰이 있다면 진행하게 할것(if)
            // 토큰이 있어서 진행이 됬다면 토큰에서 아이디값이 빼와서 화면에 보여줌
            // 버튼 누르면 delete 수행되게 함
            // DB Table에서 지워지면 자동으로 log table 에 저장되게함
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }

        return map;

    }

    //판매자 별 물품 조회
    // 127.0.0.1:8080/ROOT/seller/select_prolist?id= 판매자id&page=
    @RequestMapping(value = "/select_prolist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectProductGET(
    @RequestHeader("token") String token,
    @RequestParam(value = "page", defaultValue = "1")int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            String sellerid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            long cnt = sservice.countBySelleridProduct(sellerid); //sellerid 별 제품 갯수
            Map<String, Object> map1 = new HashMap<>();
            int rpage1 = page * PAGECNT;
            int rpage = rpage1 - (PAGECNT - 1);
            
            map1.put("page", rpage);
            map1.put("page1", rpage1);
            map1.put("id", sellerid);
            List<ProductVO> list = sservice.selectProductList(map1);
            for (ProductVO productvo : list) {
                productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
            }
            map.put("cnt", (cnt - 1) / 10 + 1); //페이지 수
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //판매자 별 주문 조회

    //판매자 별 배송 조회(송장번호 입력 용)

    //판매자 별 qna 조회(qna 제품 별 조회)
    // 127.0.0.1:8080/ROOT/seller/select_qnalist?page=
    @RequestMapping(value = "/select_qnalist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectQnaGET(
    @RequestHeader("token") String token,
    @RequestParam(value = "page", defaultValue = "1")int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            String sellerid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            long cnt = sservice.countBySelleridQna(sellerid); //sellerid 별 qna 갯수
            Map<String, Object> map1 = new HashMap<>();
            int rpage1 = page * PAGECNT;
            int rpage = rpage1 - (PAGECNT - 1);
            
            map1.put("page", rpage);
            map1.put("page1", rpage1);
            map1.put("id", sellerid);
            List<QnAVO> list = sservice.selectQnaList(map1);
            map.put("cnt", (cnt - 1) / 10 + 1); //페이지 수
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }


}
