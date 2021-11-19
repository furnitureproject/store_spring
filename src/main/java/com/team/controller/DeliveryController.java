package com.team.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Cart;
import com.team.entity.Delivery;
import com.team.entity.DeliveryProjection;
import com.team.entity.Order;
import com.team.entity.Payment;
import com.team.entity.ProductOption;
import com.team.entity.UserAddress;
import com.team.entity.UserInput;
import com.team.jwt.JwtUtil;
import com.team.repository.DeliveryRepository;
import com.team.service.CartService;
import com.team.service.DeliveryService;
import com.team.service.OrderService;
import com.team.service.PaymentService;
import com.team.service.ProductOptionService;
import com.team.service.SellerService;
import com.team.service.UserAddressService;
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

    @Autowired
    PaymentService pyService;

    @Autowired
    SellerService sService;

    @Autowired
    UserAddressService userAddressService;

    // delivery 등록
    // 127.0.0.1:8080/ROOT/delivery/insert?uno=&pno=
    // @RequestMapping(value = "/insert", method = {
    //     RequestMethod.POST}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> delInsertPOST(@RequestBody Delivery delivery,
    //     @RequestParam("uno")long no,
    //     @RequestParam("pno")long pno,
    //     @RequestHeader("token") String token){
    //     Map<String, Object> map = new HashMap<>();
    //     try{
    //         //System.out.println(delivery.toString());
    //         String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
    //         String uinputid = uiSrvice.selectUserInput(no).getOrder().getCart().getUser().getUserId(); //userinput 정보를 통해 userid 찾기
    //         UserInput userinput = uiSrvice.selectUserInput(no); //userinput no 가져오기
    //         long cartquantity = uiSrvice.selectUserInput(no).getOrder().getCart().getCartOptionCount(); //cart 수량
    //         Payment payment =pyService.selectPayment(pno); //patment 정보 받기
    //         //System.out.println(userid);
    //         //System.out.println(uinputid);
    //         if(userid.equals(uinputid)){
    //             delivery.setPayment(payment);
    //             delivery.setUserinput(userinput);
    //             dService.insertDelivery(delivery);
    //             //주문이 성공했을 때
    //             if(delivery.getDeliveryNo() != null){
    //                 //주문이 성공하면 product option quantity(전체 수량)에서 cart quantity(주문 수량)를 뺀다.
    //                 ProductOption productOption = uiSrvice.selectUserInput(no).getOrder().getCart().getProductOption(); //주문한 product option 정보 호출
    //                 Long c = cartquantity;  //주문한 cart 수량 호출
    //                 int cart = Long.valueOf(c).intValue();  //cart 수량(c) type 변경(long -> int)
    //                 productOption.setOptionQuantity(productOption.getOptionQuantity() - cart);
    //                 poService.updateProductOption(productOption);
    //                 //주문이 성공하면 cartstatus를 2로 변경한다.
    //                 Cart cart2 = uiSrvice.selectUserInput(no).getOrder().getCart();
    //                 cart2.setCartStatus(2);
    //                 cService.updateCart(cart2);
    //                 //System.out.println(cart2.getCartNo());
    //                 map.put("result", 1L);
    //             }
    //             else{
    //                 map.put("result", 0L);
    //             }
    //             map.put("result", 1L);
    //         }
    //         else{
    //             map.put("result", 0L);
    //         }
    //         // System.out.println(delivery.getDeliveryNo());   //주문 번호
    //         // System.out.println(cartquantity);   
    //         // System.out.println(poquantity);
            
    //     }
    //     catch(Exception e){
    //         map.put("result", e.hashCode());
    //     }
    //     return map;
    // }

    // @RequestMapping(value = "/insert", method = {
    //     RequestMethod.POST}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> delInsertPOST(@RequestBody Delivery[] delivery,
    //     // @RequestParam("uno")long no,
    //     // @RequestParam("pno")long pno,
    //     @RequestHeader("token") String token){
    //     Map<String, Object> map = new HashMap<>();
    //     try{
    //         //System.out.println(delivery.toString());
    //         String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
    //         String adduserid = userAddressService.
    //         // String uinputid = uiSrvice.selectUserInput(no).getOrder().getCart().getUser().getUserId(); //userinput 정보를 통해 userid 찾기
    //         // UserInput userinput = uiSrvice.selectUserInput(no); //userinput no 가져오기
    //         // long cartquantity = uiSrvice.selectUserInput(no).getOrder().getCart().getCartOptionCount(); //cart 수량
    //         // Payment payment =pyService.selectPayment(pno); //patment 정보 받기
    //         //System.out.println(userid);
    //         //System.out.println(uinputid);
    //         for(int i = 0; i < delivery.length; i++){
    //             if(userid.equals(uinputid)){
    //             for(Delivery delivery2 : list) {
                    
    //                 }
    //             //일괄 추가
    //             dService.insertAllDelivery(list);
    //             }
    //             else{
    //                 map.put("result", "등록 실패");
    //             }
    //         }
            
    //         map.put("result", 1L);
    //     }
    //         // System.out.println(delivery.getDeliveryNo());   //주문 번호
    //         // System.out.println(cartquantity);   
    //         // System.out.println(poquantity);
    //     catch(Exception e){
    //         e.printStackTrace();
    //         map.put("result", e.hashCode());
    //     }
    //     return map;
    // }

    // delivery 수정(seller)
    // 127.0.0.1:8080/ROOT/delivery/update?dno=
    // @RequestMapping(value = "/update", method = {
    //     RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> delUpdatePUT(@RequestBody Delivery delivery,
    //         @RequestParam(name = "dno", defaultValue = "0") long dno,
    //         @RequestHeader("token") String token) {
    //     Map<String, Object> map = new HashMap<>();
    //     try {
    //         String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
    //         //delivery 정보를 통해 seller id 가져오기(제품을 등록한 id)
    //         //String sellerid = dService.selectDelivery(dno).getUserinput().getOrder().getCart().getProductOption().getProduct().getSeller().getSellerId();
    //         String sellerid2 = sService.selectSellerOne(userid);
    //         //System.out.println(userid);
    //         //System.out.println(sellerid);
    //         if (userid.equals(sellerid2)) {   // delivery의 제품을 등록한 id와 로그인한 seller id가 일치하는 지 확인
    //             //System.out.println(delivery.toString());
    //             Delivery delivery2 = dService.selectDelivery(dno);
    //             delivery2.setDeliveryCode(delivery.getDeliveryCode());
    //             dService.updateDelivery(delivery2);
    //             // 송장 번호가 입력되면 cartstatus 변경
    //             // if(delivery.getDeliveryCode() != 0){
    //             //     Order order = dService.selectDelivery(dno).getUserinput().getOrder();
    //             //     order.setOrderState(3);
    //             //     //System.out.println(cart.getCartStatus());
    //             //     oService.updateOrder(order);
    //             //     map.put("result", 200L);
    //             // }
    //             // else{
    //             //     map.put("result", 300L);
    //             // }
    //             map.put("result", 1L);
    //         }
    //         else{
    //             map.put("result", 0L);
    //         }
    //         // System.out.println(dService.selectDelivery(dno).getUserinput().getOrder().getOrderNo());
    //         // System.out.println(dService.selectDelivery(dno).getUserinput().getOrder().getOrderState());
    //         // System.out.println(delivery.getDeliveryCode());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         map.put("result", e.hashCode());
    //     }
    //     return map;
    // }

    //delivery 삭제
    // 127.0.0.1:8080/ROOT/delivery/delete?dno=
    // @RequestMapping(value = "/delete", method = {
    //     RequestMethod.DELETE }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> delDelete(@RequestBody UserInput userInput,
    //     @RequestParam(name = "dno", defaultValue = "0") long dno, 
    //     @RequestHeader("token") String token) {
    //     Map<String, Object> map = new HashMap<>();
    //     try {
    //         String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
    //         //delivery 정보를 통해 seller id 가져오기
    //         String deliveryid = dService.selectDelivery(dno).getOrder().getCart().getUser().getUserId();
    //         if(userid.equals(deliveryid)){
    //             dService.deleteDelivery(dno);
    //             map.put("result", 1L);
    //         }
    //         else{
    //             map.put("result", 0L);
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         map.put("result", e.hashCode());
    //     }
    //     return map;
    // }

    //deliveryCode 1개 조회
    // 127.0.0.1:8080/ROOT/delivery/selectone?dno=
    @RequestMapping(value = "/selectone", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectOneGET(
        @RequestParam(name = "dno", defaultValue = "0") long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            Delivery delivery = dService.selectDelOne(no);
            map.put("delivery", delivery);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //delivery 조회(userid 별)
    // 127.0.0.1:8080/ROOT/delivery/uidselect?uid=
    @RequestMapping(value = "/uidselect", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectUseridGET(
        @RequestParam(name = "uid", defaultValue = "0") String id) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<DeliveryProjection> delivery = dService.selectUseridDelivery(id);
            map.put("delivery", delivery);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //delivery 조회(sellerid 별)
    // 127.0.0.1:8080/ROOT/delivery/sidselect?sid=
    @RequestMapping(value = "/sidselect", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectSelleridGET(
        @RequestParam(name = "sid", defaultValue = "0") String id) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<DeliveryProjection> delivery = dService.selectSelleridDelivery(id);
            map.put("delivery", delivery);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

}
