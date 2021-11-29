package com.team.controller.seller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Seller;
import com.team.jwt.JwtUtil;
import com.team.service.SellerService;
import com.team.vo.DeliveryVO;
import com.team.vo.ProductVO;
import com.team.vo.QnAVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    
    // 판매자 계정 생성
    // 127.0.0.1:8080/ROOT/seller/insert
    @RequestMapping(value="/insert", method=RequestMethod.POST,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> registSellerPost(@RequestBody Seller seller) {
        Map<String, Object> map = new HashMap<>();
        try{
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            seller.setSellerPw(bcpe.encode(seller.getSellerPw()));
            sservice.insertSeller(seller);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }
        return map;
    }

    // 판매자 정보 조회
    // 127.0.0.1:8080/ROOT/seller/update
    @RequestMapping(value = "/update", method = RequestMethod.GET,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSellerInfo(
        @RequestHeader("token") String token){
        Map<String, Object> map = new HashMap<>();
        try{
            String sellerid = jwtUtil.extractUsername(token); // token을 통해 판매자 정보 찾기
            Seller oldSeller = sservice.selectSellerOne(sellerid);
            map.put("seller", oldSeller);
            map.put("status", 200);
        
        }catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }
        return map;
    }

    // 판매자정보 수정
    // 127.0.0.1:8080/ROOT/seller/update
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> postSellerInfo(@RequestBody Seller seller,
        @RequestHeader("token") String token){
        Map<String, Object> map = new HashMap<>();
        try{
            String sellerid = jwtUtil.extractUsername(token); // token을 통해 판매자 정보 찾기
            Seller oldSeller = sservice.selectSellerOne(sellerid);
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            
            System.out.println(sellerid.equals(oldSeller.getSellerId()));
            System.out.println(bcpe.matches(seller.getSellerPw(), oldSeller.getSellerPw()));
            System.out.println(seller.toString());
            if(sellerid.equals(oldSeller.getSellerId()) && bcpe.matches(seller.getSellerPw(), oldSeller.getSellerPw())){
                if(seller.getSellerNewPw() != null) {
                    oldSeller.setSellerPw(bcpe.encode(seller.getSellerNewPw())); // 새로운 비밀번호
                }
                if(seller.getSellerEmail() !=  null){
                    oldSeller.setSellerEmail(seller.getSellerEmail());  //메일
                }
                if(seller.getSellerPhone() != null){
                    oldSeller.setSellerPhone(seller.getSellerPhone());  //연락처
                }
                if(seller.getStorePhone() != null){
                    oldSeller.setStorePhone(seller.getStorePhone());    //매장 연락처
                }
                // System.out.println(oldSeller.toString());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                oldSeller.setSellerEditdate(timestamp); //수정 날짜
                int updateCheck = sservice.updateSeller(oldSeller);
                if(updateCheck == 1){
                    map.put("status", 200);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }
        return map;
    }

    // 판매자 계정 삭제
    // 127.0.0.1:8080/ROOT/seller/delete
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteSeller(@RequestBody Seller seller,
        @RequestHeader("token") String token){
        Map<String, Object> map = new HashMap<>();
        try{
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            String sellerid = jwtUtil.extractUsername(token);
            String sellerpw = sservice.selectSellerOne(sellerid).getSellerPw();
            if(sellerid.equals(seller.getSellerId())){  //로그인 한 id와 입력한 id가 같은지 비교
                if(bcpe.matches(seller.getSellerPw(), sellerpw)){
                    sservice.deleteSeller(seller);
                    map.put("status", 200);
                }
            }
            else{
                map.put("status", 300);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }
        return map;
    }

    //판매자 별 물품 조회
    // 127.0.0.1:8080/ROOT/seller/select_prolist?page=1
    @RequestMapping(value = "/select_prolist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectProductGET(
    @RequestHeader("token") String token,
    @RequestParam(value = "page", defaultValue = "1")int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            String sellerid = jwtUtil.extractUsername(token); // token을 통해 판매자 정보 찾기
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
            map.put("result", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //판매자 별 주문 조회
    // 127.0.0.1:8080/ROOT/seller/select_dellist?page=1
    @RequestMapping(value = "/select_dellist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectDelGET(
    @RequestHeader("token") String token,
    @RequestParam(value = "page", defaultValue = "1")int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            String sellerid = jwtUtil.extractUsername(token); // token을 통해 판매자 정보 찾기
            long cnt = sservice.countBySelleridDelivery(sellerid); //sellerid 별 delivery 갯수
            Map<String, Object> map1 = new HashMap<>();
            int rpage1 = page * PAGECNT;
            int rpage = rpage1 - (PAGECNT - 1);
            map1.put("page", rpage);
            map1.put("page1", rpage1);
            map1.put("id", sellerid);
            List<DeliveryVO> list = sservice.selectDelList(map1);
            map.put("cnt", (cnt - 1) / 10 + 1); //페이지 수
            map.put("list", list);
            map.put("result", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //판매자 별 qna 조회(qna 제품 별 조회)
    // 127.0.0.1:8080/ROOT/seller/select_qnalist?page=1
    @RequestMapping(value = "/select_qnalist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectQnaGET(
    @RequestHeader("token") String token,
    @RequestParam(value = "page", defaultValue = "1")int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            String sellerid = jwtUtil.extractUsername(token); // token을 통해 판매자 정보 찾기
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
            map.put("result", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }


}
