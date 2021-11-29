package com.team.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Cart;
import com.team.entity.CartProjection;
import com.team.entity.ProductOption;
import com.team.entity.User;
import com.team.enums.OrderStatus;
import com.team.enums.Status;
import com.team.jwt.JwtUtil;
import com.team.service.CartService;
import com.team.service.OrderService;
import com.team.service.ProductOptionService;
import com.team.service.UserService;
import com.team.vo.CartVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CartController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CartService cService;

    @Autowired
    ProductOptionService pOService;

    @Autowired
    UserService uService;

    @Autowired
    OrderService oService;

    @GetMapping(value = "/cart", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCart(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            List<CartVO> list1 = new ArrayList<>();
            List<CartProjection> list = cService.selectAllUserCart(userid);
            for (int i = 0; i < list.size(); i++) {
                CartProjection cart = list.get(i);
                Long number = cart.getProductOption_Product_ProductCode();
                CartVO cart1 = new CartVO();
                cart1.setCartNo(cart.getCartNo());
                cart1.setCartImgName("/ROOT/product/select_image?productCode=" + number);
                cart1.setCartOptionCount(cart.getCartOptionCount());
                cart1.setCartOptionPrice(cart.getProductOption_OptionPrice());
                cart1.setCartStatus(cart.getCartStatus());
                cart1.setCartCode(cart.getProductOption_Product_ProductCode());
                cart1.setCartName(cart.getProductOption_Product_ProductTitle());
                cart1.setCartOptionName(cart.getProductOption_OptionName());
                cart1.setUser(cart.getUser_UserId());
                if (cart1.getCartStatus() == 0 || cart1.getCartStatus() == 1) {
                    list1.add(cart1);
                }
            }
            map.put("status", Status.COMPLETE.getCode());
            map.put("list", list1);

        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/cart/test", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCarttest(@RequestHeader("token") String token,
            @RequestParam(defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            int page1 = Math.max(page, 1);
            PageRequest pageRequest = PageRequest.of(page1 - 1, 10);
            List<CartVO> list1 = new ArrayList<>();
            List<CartProjection> list = cService.selectAllUserCart(userid);
            for (int i = 0; i < list.size(); i++) {
                CartProjection cart = list.get(i);
                Long number = cart.getProductOption_Product_ProductCode();
                CartVO cart1 = new CartVO();
                if (cart1.getCartStatus() == 0 || cart1.getCartStatus() == 1) {
                    cart1.setCartNo(cart.getCartNo());
                    cart1.setCartImgName("/ROOT/product/select_image?productCode=" + number);
                    cart1.setCartOptionCount(cart.getCartOptionCount());
                    cart1.setCartOptionPrice(cart.getProductOption_OptionPrice());
                    cart1.setCartStatus(cart.getCartStatus());
                    cart1.setCartCode(cart.getProductOption_Product_ProductCode());
                    cart1.setCartName(cart.getProductOption_Product_ProductTitle());
                    cart1.setCartOptionName(cart.getProductOption_OptionName());
                    cart1.setUser(cart.getUser_UserId());
                    list1.add(cart1);
                }
            }
            final int start = (int) pageRequest.getOffset();
            final int end = Math.min((start + pageRequest.getPageSize()), list1.size());
            Page<CartVO> list2 = new PageImpl<>(list1.subList(start, end), pageRequest, list1.size());
            map.put("status", Status.COMPLETE.getCode());
            map.put("list", list2);

        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/cart_one", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCartOne(@RequestHeader("token") String token, @RequestParam("cartno") Long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            CartProjection cart = cService.selectCartProjectionOne(no);
            Long no1 = cService.selectCartOne(no).getProductOption().getProduct().getProductCode();
            if (cart.getUser_UserId().equals(userid)) {
                map.put("status", 200);
                map.put("cart", cart);
                map.put("image", "/ROOT/product/select_image?productCode=" + no1);
            } else {
                map.put("status", Status.ERROR.getStatus());
            }

        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/cart", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertCart(@RequestBody Cart[] cart, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user = uService.selectUserOne(userid);
            for (int i = 0; i < cart.length; i++) {
                if (cart[i] != null) {
                    Long code = cart[i].getProductOption().getOptionCode();
                    ProductOption productOption = pOService.selectProductOptionOne(code);
                    cart[i].setUser(user);
                    cart[i].setProductOption(productOption);
                    cService.insertCart(cart[i]);
                    map.put("status", Status.COMPLETE.getCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/cart", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateCart(@RequestBody Cart cart, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            Cart cart1 = cService.selectCartOne(cart.getCartNo());
            if (cart1.getUser().getUserId().equals(userid)) {
                cart1.setCartOptionCount(cart.getCartOptionCount());
                // cart1.setProductOption(pOService.selectProductOptionOne(code));
                cService.updateCart(cart1);
                map.put("status", Status.COMPLETE.getCode());
            } else {
                map.put("status", Status.ERROR.getStatus());
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/cart", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteCart(@RequestBody Long[] no, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            for (int i = 0; i < no.length; i++) {
                long num = no[i];
                if (cService.selectCartProjectionOne(num).getUser_UserId().equals(userid)) {
                    if (oService.selectOrderForCartNo(num) != null) {
                        long orderno = oService.selectOrderForCartNo(num).getOrderNo();
                        Long[] orderlist = new Long[1];
                        orderlist[0] = orderno;
                        oService.deleteOrder(orderno);
                    }
                    cService.deleteCart(num);
                    map.put("status", Status.COMPLETE.getCode());
                } else {
                    map.put("status", Status.ERROR.getStatus());
                }
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/cart1")
    public Map<String, Object> Cart(Cart cart) {
        Map<String, Object> map = new HashMap<>();
        cService.insertCart(cart);
        System.out.println(cart.toString());
        map.put("cart", cart);
        return map;
    }

}
