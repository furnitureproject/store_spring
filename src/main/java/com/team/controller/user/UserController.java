package com.team.controller.user;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.User;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService uService;

    @GetMapping(value = "/join")
    public Map<String, Object> joinUserGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        return map;
    }

    @PostMapping(value = "/join", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> joinUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (uService.selectUserOne(user.getUserId()) == null) {
                // BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
                // user.setUserPw(bcpe.encode(user.getUserPw()));
                uService.insertUser(user);
                map.put("status", 200);
            } else {
                map.put("status", 484);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/select", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {

            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteUserGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        return map;
    }

    @DeleteMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (uService.selectUserOne(user.getUserId()) != null)
                uService.deleteUser(user);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            uService.updateUser(user);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateUserGET(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {

            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
