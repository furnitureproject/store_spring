package com.team.controller;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Cart;
import com.team.entity.Delivery;
import com.team.entity.Order;
import com.team.entity.ProductOption;
import com.team.entity.UserInput;
import com.team.jwt.JwtUtil;
import com.team.service.CartService;
import com.team.service.DeliveryService;
import com.team.service.OrderService;
import com.team.service.ProductOptionService;
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

    @Autowired
    CartService cService;

    @Autowired
    OrderService oService;

    @Autowired
    ProductOptionService poService;

    // delivery 등록
    // 127.0.0.1:8080/ROOT/delivery/insert?uno=
    @RequestMapping(value = "/insert", method = {
        RequestMethod.POST}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> delInsertPOST(@RequestBody Delivery delivery,
        @RequestParam("uno")long no,
        @RequestHeader("token") String token){
        Map<String, Object> map = new HashMap<>();
        try{
            //System.out.println(delivery.toString());
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            String uinputid = uiSrvice.selectUserInput(no).getOrder().getCart().getUser().getUserId(); //userinput 정보를 통해 userid 찾기
            UserInput userinput = uiSrvice.selectUserInput(no); //userinput no 가져오기
            long cartquantity = uiSrvice.selectUserInput(no).getOrder().getCart().getCartOptionCount(); //cart 수량
            long poquantity = uiSrvice.selectUserInput(no).getOrder().getCart().getProductOption().getOptionQuantity(); //product option 수량
            //System.out.println(userid);
            //System.out.println(uinputid);
            if(userid.equals(uinputid)){
                delivery.setUserinput(userinput);
                dService.insertDelivery(delivery);
                map.put("result", 1L);
            }
            else{
                map.put("result", 0L);
            }
            System.out.println(delivery.getDeliveryNo());
            System.out.println(cartquantity);
            System.out.println(poquantity);
            //주문이 성공하면 product option quantity에서 cart quantity를 뺀다.
            if(delivery.getDeliveryNo() != null){
                ProductOption productOption = uiSrvice.selectUserInput(no).getOrder().getCart().getProductOption(); //주문한 product option 정보 호출
                //int cart = uiSrvice.selectUserInput(no).getOrder().getCart().getCartOptionCount();  //주문한 cart 수량 호출
                //productOption.setOptionQuantity(productOption.getOptionQuantity() - cart);
                poService.updateProductOption(productOption);
            }
            else{

            }
        }
        catch(Exception e){
            map.put("result", e.hashCode());
        }
        return map;
    }

    // delivery 수정(seller)
    // 127.0.0.1:8080/ROOT/delivery/update?dno=
    @RequestMapping(value = "/update", method = {
        RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> qnaUpdatePUT(@RequestBody Delivery delivery,
            @RequestParam(name = "dno", defaultValue = "0") long dno,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            //delivery 정보를 통해 seller id 가져오기
            String sellerid = dService.selectDelivery(dno).getUserinput().getOrder().getCart().getProductOption().getProduct().getSeller().getSellerId();
            //System.out.println(userid);
            //System.out.println(sellerid);
            if (userid.equals(sellerid)) {   // 회원정보를 통해 seller 찾기
                //System.out.println(delivery.toString());
                Delivery delivery2 = dService.selectDelivery(dno);
                delivery2.setDeliveryCode(delivery.getDeliveryCode());
                dService.updateDelivery(delivery2);
                map.put("result", 1L);
            }
            else{
                map.put("result", 0L);
            }
            // System.out.println(dService.selectDelivery(dno).getUserinput().getOrder().getOrderNo());
            // System.out.println(dService.selectDelivery(dno).getUserinput().getOrder().getOrderState());
            // System.out.println(delivery.getDeliveryCode());
            // 송장 번호가 입력되면 cartstatus 변경
            if(delivery.getDeliveryCode() != null){
                Order order = dService.selectDelivery(dno).getUserinput().getOrder();
                order.setOrderState(3);
                //System.out.println(cart.getCartStatus());
                oService.updateOrder(order);
                map.put("result", 200L);
            }
            else{
                map.put("result", 300L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //delivery 삭제
    // 127.0.0.1:8080/ROOT/delivery/delete?dno=
    @RequestMapping(value = "/delete", method = {
        RequestMethod.DELETE }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productDelete(@RequestBody UserInput userInput,
        @RequestParam(name = "dno", defaultValue = "0") long dno, 
        @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            //delivery 정보를 통해 seller id 가져오기
            String deliveryid = dService.selectDelivery(dno).getUserinput().getOrder().getCart().getUser().getUserId();
            if(userid.equals(deliveryid)){
                dService.deleteDelivery(dno);
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
