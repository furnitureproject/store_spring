package com.team.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Cart;
import com.team.entity.Delivery;
import com.team.entity.DeliveryProjection;
import com.team.entity.Order;
import com.team.entity.ProductOption;
import com.team.jwt.JwtUtil;
import com.team.service.CartService;
import com.team.service.DeliveryService;
import com.team.service.OrderService;
import com.team.service.PaymentService;
import com.team.service.ProductOptionService;
import com.team.service.SellerService;
import com.team.service.UserAddressService;
import com.team.service.UserService;
import com.team.vo.DeliveryVO;

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

    int PAGECNT = 10;
    
    // delivery 등록
    // 127.0.0.1:8080/ROOT/delivery/insert
    // [{"order":{"orderNo":10}, "payment":{"paymentNo":6},"userAddress":{"addressNo":1}}, { }]
    @RequestMapping(value = "/insert", method = {
        RequestMethod.POST}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> delInsertPOST(@RequestBody Delivery[] delivery,
        @RequestHeader("token") String token){
        Map<String, Object> map = new HashMap<>();
        try{
            //System.out.println(delivery.toString());
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            // System.out.println(userid);
            for(int i = 0; i < delivery.length; i++){
                Long no = delivery[i].getUserAddress().getAddressNo(); //adressNo 불러오기
                Long ono = delivery[i].getOrder().getOrderNo(); //orderNo 불러오기
                String id = oService.selectOrderOne(ono).getCart().getProductOption().getProduct().getSeller().getSellerId();   //sellerid 불러오기
                delivery[i].setUserAddress(userAddressService.selectUserAddressOne(no));
                if(delivery[i].getUserAddress().getUser().getUserId().equals(userid)){
                //seller 정보 set
                delivery[i].setSeller(sService.selectSellerOne(id));
                //일괄 추가
                dService.insertDelivery(delivery[i]);
                map.put("result", 1L);
                }
                else{
                    map.put("result", 0L);
                }
                
                //주문이 성공했을 때
                if(delivery[i].getDeliveryNo() != null){
                    //System.out.println(ono);
                    //주문이 성공하면 product option quantity(전체 수량)에서 cart quantity(주문 수량)를 뺀다.
                    ProductOption productOption = oService.selectOrderOne(ono).getCart().getProductOption(); //주문한 product option 정보 호출
                    long cartquantity = oService.selectOrderOne(ono).getCart().getCartOptionCount();    //cart 수량
                    //System.out.println(cartquantity);
                    int cart = Long.valueOf(cartquantity).intValue();  //cart 수량(cartquantity) type 변경(long -> int)
                    productOption.setOptionQuantity(productOption.getOptionQuantity() - cart);
                    poService.updateProductOption(productOption);

                    //주문이 성공하면 cartstatus를 2로 변경한다.
                    Cart cart2 = oService.selectOrderOne(ono).getCart();
                    cart2.setCartStatus(2);
                    cService.updateCart(cart2);
                    // System.out.println(cart2.getCartNo());
                    map.put("result", 200L);
                }
                else{
                    map.put("result", 300L);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // delivery 수정(seller)
    // 127.0.0.1:8080/ROOT/delivery/update
    // [{"deliveryNo":93,"deliveryCode":123456}, { }]
    @RequestMapping(value = "/update", method = {
        RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> delUpdatePUT(@RequestBody Delivery[] delivery,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            for(int i = 0; i < delivery.length; i++){
                //delivery 정보를 통해 seller id 가져오기(제품을 등록한 id)
                Long dno = delivery[i].getDeliveryNo(); //deliveryNo 불러오기
                // System.out.println(dno);
                String sellerid = dService.selectDelivery(dno).getSeller().getSellerId();   //sellerid 불러오기
                
                if (sellerid.equals(userid)) {   // delivery의 제품을 등록한 id와 로그인한 seller id가 일치하는 지 확인
                    Delivery delivery2 = dService.selectDelivery(dno);
                    // delivery2.setDeliveryNo(dno);
                    delivery2.setDeliveryCode(delivery[i].getDeliveryCode());
                    dService.updateDelivery(delivery2);

                    // 송장 번호가 입력되면 cartstatus 변경
                    long delivery2no = delivery2.getDeliveryNo();
                    // System.out.println(delivery2no);
                    if(delivery2no != 0){
                        Order order = dService.selectDelivery(delivery2no).getOrder();
                        order.setOrderState(3);
                        oService.updateOrder(order);
                        map.put("result", 200L);
                    }
                    else{
                        map.put("result", 300L);
                    }
                    map.put("result", 1L);
                }
                else{
                    map.put("result", 0L);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //delivery 삭제
    // 127.0.0.1:8080/ROOT/delivery/delete
    // [{"deliveryNo":80}, { }]
    @RequestMapping(value = "/delete", method = {
        RequestMethod.DELETE }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> delDelete(@RequestBody Delivery[] delivery,
        @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원정보 찾기
            // for(Delivery user:delivery){
            //     long deliveryno = user.getDeliveryNo();
            //     String deliveryid = dService.selectDelivery(deliveryno).getSeller().getSellerId();
            //     if(userid.equals(deliveryid)) {
            //         dService.deleteDelivery(deliveryno);
            //         map.put("result", 1L);
            //     }
            //     else{
            //         map.put("result", 0L);
            //     }
            // }
            
            for(int i = 0; i < delivery.length; i++){
                //delivery 정보를 통해 seller id 가져오기(제품을 등록한 id)
                Long deliveryno = delivery[i].getDeliveryNo(); //orderNo 불러오기
                // System.out.println(dno);
                String sellerid = dService.selectDelivery(deliveryno).getSeller().getSellerId();   //sellerid 불러오기
                if(userid.equals(sellerid)) {
                    dService.deleteDelivery(deliveryno);
                    map.put("result", 1L);
                }
                else{
                    map.put("result", 0L);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

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
    // 127.0.0.1:8080/ROOT/delivery/uidselect
    // 리턴값 : {"deliveryNo": , "deliveryCode": }
    @RequestMapping(value = "/uidselect", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectUseridGET(
        @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 회원 정보 찾기
            List<DeliveryProjection> delivery = dService.selectUseridDelivery(userid);
            map.put("delivery", delivery);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //delivery 조회(sellerid 별)
    // 127.0.0.1:8080/ROOT/delivery/sidselect
    // 리턴값 : {"deliveryNo": , "deliveryCode": }
    @RequestMapping(value = "/sidselect", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectSelleridGET(
        @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String sellerid = jwtUtil.extractUsername(token); // token을 통해 판매자 정보 찾기
            List<DeliveryProjection> delivery = dService.selectSelleridDelivery(sellerid);
            map.put("delivery", delivery);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //delivery 조회(userid 별)
    // 127.0.0.1:8080/ROOT/delivery/select_userdellist?page=
    @RequestMapping(value = "/select_userdellist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectUidQnaGET(
    @RequestHeader("token") String token,
    @RequestParam(value = "page", defaultValue = "1")int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token); // token을 통해 판매자 정보 찾기
            long cnt = dService.countByUseridDelivery(userid); //userid 별 delivery 갯수
            Map<String, Object> map1 = new HashMap<>();
            int rpage1 = page * PAGECNT;
            int rpage = rpage1 - (PAGECNT - 1);
            
            map1.put("page", rpage);
            map1.put("page1", rpage1);
            map1.put("id", userid);
            List<DeliveryVO> list = dService.selectUserDelList(map1);
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
