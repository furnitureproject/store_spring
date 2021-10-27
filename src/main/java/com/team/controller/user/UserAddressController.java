package com.team.controller.user;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.User;
import com.team.entity.UserAddress;
import com.team.repository.UserAddressRepository;
import com.team.service.UserAddressService;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @Autowired
    UserAddressRepository uaRepository;

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
            System.out.println(uaRepository.findByUser_UserId("aaa"));

            map.put("status", 200);
            map.put("list", uaRepository.findByUser_UserId(id));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/one", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userAddressOneGET(@RequestParam("adno") long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", 200);
            map.put("obj", uaService.selectUserAddressOne(no));
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

}