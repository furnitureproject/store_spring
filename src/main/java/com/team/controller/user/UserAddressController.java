package com.team.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.User;
import com.team.entity.UserAddress;
import com.team.entity.UserAddressProjection;
import com.team.enums.OrderStatus;
import com.team.enums.Status;
import com.team.jwt.JwtUtil;
import com.team.service.UserAddressService;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping(value = "/address")
public class UserAddressController {

    @Autowired
    UserService uService;

    @Autowired
    UserAddressService uaService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(value = "/insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userAddressInsertPOST(@RequestBody UserAddress address,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String id = jwtUtil.extractUsername(token);
            User user = uService.selectUserOne(id);
            address.setUser(user);
            uaService.insertUserAddress(address);
            map.put("status", OrderStatus.COMPLETE.getStatus());
            map.put("address", address);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/list", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userAddressListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user = uService.selectUserOne(userid);
            String id = user.getUserId();

            map.put("status", Status.COMPLETE.getCode());
            map.put("list", uaService.selectOneUserAddressList(id));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/one", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userAddressOneGET(@RequestParam("addressno") long no,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            if (uaService.selectUserAddressOne(no).getUser().getUserId().equals(userid)) {
                map.put("status", Status.COMPLETE.getCode());
                map.put("obj", uaService.selectUserAddressOneProjection(no));
            } else {
                // address를 넣은 User와 같은 유저가 아닐 경우 오류 반환
                map.put("status", Status.ERROR.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> useraddressUpdatePUT(@RequestBody UserAddress address,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            if (address.getUser().getUserId().equals(userid)) {
                uaService.updateUserAddress(address);
                map.put("status", Status.COMPLETE.getCode());
            } else {
                // address를 넣은 User와 같은 유저가 아닐 경우 오류 반환
                map.put("status", Status.ERROR.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> useraddressdeletePUT(@RequestParam("addressno") long adno,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            List<UserAddressProjection> list = uaService.selectOneUserAddressList(userid);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUser_UserId().equals(userid)) {
                    uaService.deleteUserAddress(adno);
                    map.put("status", Status.COMPLETE.getCode());
                } else {
                    // address를 넣은 User와 같은 유저가 아닐 경우 오류 반환
                    map.put("status", Status.ERROR.getStatus());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // @GetMapping(value = "/user")
    // public Map<String, Object> searchaddress(@RequestHeader("token") String
    // token) {
    // Map<String, Object> map = new HashMap<>();
    // String userid = jwtUtil.extractUsername(token);
    // UserAddressProjection ua =
    // uaService.selectUserAddressOneOrderByAddressNo(userid);
    // map.put("address", ua);
    // return map;
    // }

}