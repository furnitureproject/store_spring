package com.team.controller;

import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Cart;
import com.team.entity.Order;
import com.team.entity.User;
import com.team.jwt.JwtUtil;
import com.team.service.CartService;
import com.team.service.OrderService;
import com.team.service.ProductOptionService;
import com.team.service.UserService;
import com.team.vo.OrderVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class OrderController {

    @Autowired
    UserService uService;

    @Autowired
    OrderService oService;

    @Autowired
    CartService cService;

    @Autowired
    ProductOptionService pOService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping(value = "/order")
    public Map<String, Object> userOrderListGet(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("userid", userid);
            List<OrderVO> sum = oService.selectQueryUserOrder(map1);
            map.put("status", 200);
            // 물품 별 금액 총합이 아닌 그 프론트에 나타나는 금액의 총합값도 나오도록 해줄 것
            map.put("list", oService.selectOrderUser(userid));
            map.put("sum", sum);
            for (int i = 0; i < sum.size(); i++) {
                Long optioncode = sum.get(i).getOptionCode();
                Long productcode = pOService.selectProductOptionOne(optioncode).getProduct().getProductCode();
                map.put("image" + i, "/ROOT/product/select_image?productCode=" + productcode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/order_one")
    public Map<String, Object> oneOrderGet(@RequestParam("orderno") Long no, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user = uService.selectUserOne(userid);
            Long proimg = oService.selectOrderOne(no).getCart().getProductOption().getProduct().getProductCode();
            if (oService.selectOrderOne(no).getCart().getUser().equals(user)) {
                map.put("status", 200);
                map.put("order", oService.selectOrderProjectionOne(no));
                map.put("image", "/ROOT/product/select_image?productCode=" + proimg);
            } else {
                map.put("status", "적합한 권한을 가지고 있지 않습니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/order")
    public Map<String, Object> userOrderPost(@RequestBody Order[] order, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user = uService.selectUserOne(userid);
            for (int i = 0; i < order.length; i++) {
                Long no = order[i].getCart().getCartNo();
                order[i].setCart(cService.selectCartOne(no));
                if (order[i].getCart().getUser().getUserId().equals(userid)) {
                    Cart cart = cService.selectCartOne(no);
                    int price = Math.toIntExact(cart.getProductOption().getOptionPrice() * cart.getCartOptionCount());
                    System.out.println(cart.getCartOptionCount());
                    user.setUserPoint((int) user.getUserPoint() + (int) (price * 0.01));
                    oService.insertOrder(order[i]);
                    map.put("status", 200);
                } else {
                    map.put("status", "적합한 권한을 가지고 있지 않습니다");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/order")
    public Map<String, Object> updateOrderPut(@RequestHeader("token") String token, @RequestBody Order order) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            Order order1 = oService.selectOrderOne(order.getOrderNo());
            if (order1.getCart().getUser().getUserId().equals(userid)) {
                oService.updateOrder(order);
                map.put("status", 200);
            } else {
                map.put("status", "적합한 권한을 가지고 있지 않습니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/order")
    public Map<String, Object> orderDelete(@RequestHeader("token") String token, @RequestBody Long[] no) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user = uService.selectUserOne(userid);
            for (int i = 0; i < no.length; i++) {
                if (oService.selectOrderOne(no[i]).getCart().getUser().equals(user)) {
                    Order order = oService.selectOrderOne(no[i]);
                    System.out.println(order.getOrderNo());
                    Long cartno = order.getCart().getCartNo();
                    Cart cart = cService.selectCartOne(cartno);
                    int price = Math.toIntExact(cart.getProductOption().getOptionPrice() * cart.getCartOptionCount());
                    user.setUserPoint((int) user.getUserPoint() - (int) (price * 0.01));
                    oService.deleteOrder(no[i]);
                }
            }
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // TEST 중입니다
    // 월 별 전체 판매액
    @GetMapping(value = "/order/date")
    public Map<String, Object> dateSearch(@RequestParam("month") String month2, @RequestParam("year") String year2) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Order> list = oService.selectAllOrder();
            List<Long> list1 = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Date date1 = list.get(i).getOrderDate();
                Long orderno = list.get(i).getOrderNo();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateToStr = dateFormat.format(date1);
                String[] timesplit = dateToStr.split("/");
                String Month1 = timesplit[1];
                String Year1 = timesplit[2];
                if (Month1.equals(month2) && Year1.equals(year2)) {
                    long Price = oService.selectOrderOne(orderno).getCart().getProductOption().getOptionPrice();
                    long Quantity = oService.selectOrderOne(orderno).getCart().getCartOptionCount();
                    list1.add(Price * Quantity);
                    long sum = 0;
                    for (int j = 0; j < list1.size(); j++) {
                        sum += list1.get(j);
                    }
                    System.out.println(sum);
                    map.put(year2 + "년" + month2 + "월 판매액", sum);
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/order/seller")
    public Map<String, Object> sellingPriceUser(@RequestParam("month") String month2,
            @RequestParam("year") String year2, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        String sellerid = jwtUtil.extractUsername(token);
        try {
            List<Order> list = oService.selectAllOrder();
            List<Long> list1 = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Date date1 = list.get(i).getOrderDate();
                Long orderno = list.get(i).getOrderNo();
                if (oService.selectOrderOne(orderno).getCart().getProductOption().getProduct().getSeller().getSellerId()
                        .equals(sellerid)) {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String dateToStr = dateFormat.format(date1);
                    String[] timesplit = dateToStr.split("/");
                    String Month1 = timesplit[1];
                    String Year1 = timesplit[2];
                    if (Month1.equals(month2) && Year1.equals(year2)) {
                        long Price = oService.selectOrderOne(orderno).getCart().getProductOption().getOptionPrice();
                        long Quantity = oService.selectOrderOne(orderno).getCart().getCartOptionCount();
                        list1.add(Price * Quantity);
                        long sum = 0;
                        for (int j = 0; j < list1.size(); j++) {
                            sum += list1.get(j);
                        }
                        System.out.println(sum);
                        map.put(year2 + "년" + month2 + "월 판매액", sum);
                        map.put("status", 200);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
