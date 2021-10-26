package com.team.controller.seller;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Seller;
import com.team.service.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping(value = "/seller")
public class SellerController {
    
    @Autowired
    SellerService sservice;

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
    @RequestMapping(value = "/info", method = RequestMethod.GET,
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
    @RequestMapping(value = "/info", method = RequestMethod.POST,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> postSellerInfo(@RequestBody Seller seller){
        Map<String, Object> map = new HashMap<>();
        try{
            Seller oldSeller = sservice.selectSellerOne(seller.getSellerId());
            // if(seller.getSellerEmail() != null){
            //     oldSeller.setSellerEmail(seller.getSellerEmail());            
            // }
            oldSeller.setSellerEmail(seller.getSellerEmail());    
            oldSeller.setSellerPhone(seller.getSellerPhone());
            oldSeller.setStorePhone(seller.getStorePhone());
            int updateCheck = sservice.updateSeller(oldSeller);
            if(updateCheck == 1){

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

}
