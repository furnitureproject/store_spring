package com.team.controller.user;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.User;
import com.team.entity.UserAddress;
import com.team.entity.UserAddressProjection;
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

@RestController
@RequestMapping(value = "/address")
public class UserAddressController {

    @Autowired
    UserService uService;

    @Autowired
    UserAddressService uaService;

    @PostMapping(value = "/insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userAddressInsertPOST(@RequestBody UserAddress address) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = uService.selectUserOne("aaa");
            address.setUser(user);
            uaService.insertUserAddress(address);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/list", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userAddressListGET() {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = uService.selectUserOne("aaa");
            String id = user.getUserId();

            map.put("status", 200);
            map.put("list", uaService.selectOneUserAddressList(id));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/one", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userAddressOneGET(@RequestParam("addressno") long no, User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = user.getUserId();
            if (uaService.selectUserAddressOne(no).getUser().getUserId().equals(userid)) {
                map.put("status", 200);
                map.put("obj", uaService.selectUserAddressOneProjection(no));
            } else {
                map.put("status", 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> useraddressUpdatePUT(@RequestBody UserAddress address) {
        Map<String, Object> map = new HashMap<>();
        try {
            uaService.updateUserAddress(address);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> useraddressdeletePUT(@RequestParam("addressno") long adno,
            @RequestBody UserAddressProjection address) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = new User();
            if (uaService.selectOneUserAddressList(user.getUserId()).contains(address)) {
                uaService.deleteUserAddress(adno);
                map.put("status", 200);
            } else {
                map.put("status", 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}