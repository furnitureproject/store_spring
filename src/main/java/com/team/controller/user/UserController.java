package com.team.controller.user;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.User;
import com.team.repository.UserRepository;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService uService;

    @Autowired
    UserRepository uRepository;

    // 회원 가입
    @GetMapping(value = "/join")
    public Map<String, Object> joinUserGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        return map;
    }

    // 회원 가입(User entity 값)
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

    // 아이디 찾기()
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

    // 회원 탈퇴
    @GetMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteUserGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        return map;
    }

    // 회원 탈퇴(userId, userPw)
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

    // 회원 정보 수정(userEmail, userPhone)
    @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateUser(@RequestBody User user, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (user.getUserEmail() == null) {
                map.put("status", 100);
            } else if (user.getUserPhone() == null) {
                map.put("status", 101);
            } else {
                User user1 = uService.selectUserOne(user.getUserId());
                user1.setUserPhone(user.getUserPhone());
                user1.setUserEmail(user.getUserEmail());
                uService.updateUser(user);
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 회원 정보 수정(User)
    @GetMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateUserGET() {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = new User();
            user.setUserId("aaa");
            String id = user.getUserId();
            map.put("status", 200);
            map.put("obj", uRepository.findByuserId(id));

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 비밀번호 수정(userPw)
    @PostMapping(value = "/pwchange", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> pwchangeUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user1 = uService.selectUserOne(user.getUserId());
            System.out.println(user.getUserPw().getClass().getName());
            System.out.println(user1.getUserPw().getClass().getName());
            if (user1.getUserPw().equals(user.getUserPw())) {
                map.put("status", 100);
            } else if (user1.getUserPw() != user.getUserPw()) {
                user1.setUserPw(user.getUserPw());
                uService.updateUser(user1);
                map.put("status", 200);
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }

        return map;
    }

    // 비밀번호 확인(userPw)
    @PostMapping(value = "/pwcheck", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> pwcheck(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user1 = uService.selectUserOne(user.getUserId());
            if (user1.getUserPw().equals(user.getUserPw())) {
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