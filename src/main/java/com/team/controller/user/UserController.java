package com.team.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.team.entity.User;
import com.team.jwt.JwtUtil;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    JwtUtil jwtUtil;

    // TEST
    // @GetMapping(value = "/login")
    // public Map<String, Object> loginUserGET(HttpServletRequest request) {
    // Map<String, Object> map = new HashMap<>();
    // System.out.println(request.getSession());
    // map.put("status", 200);
    // return map;
    // }

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
                BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
                user.setUserPw(bcpe.encode(user.getUserPw()));
                // TEST용 추가
                String phone = user.getUserPhone();
                String newphone = phone.toString().replaceAll("-", "");
                user.setUserPhone(newphone);

                uService.insertUser(user);
                map.put("status", 200);
            } else {
                // 이미 가입한 User의 아이디를 사용한 경우 오류 반환
                map.put("status", "같은 아이디가 존재하고 있습니다");
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
    public Map<String, Object> deleteUser(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user = uService.selectUserOne(userid);
            if (uService.selectUserOne(user.getUserId()) != null) {
                uService.deleteUser(user);
                map.put("status", 200);
            } else {
                map.put("status", "존재하지 않는 유저입니다");
            }
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
                // 이메일을 넣지 않은 경우
                map.put("status", "이메일을 입력하지 않았습니다");
            } else if (user.getUserPhone() == null) {
                // phone을 넣지 않은 경우
                map.put("status", "전화번호를 입력하지 않았습니다");
            } else {
                String userid = jwtUtil.extractUsername(token);
                User user1 = uService.selectUserOne(userid);
                user1.setUserPhone(user.getUserPhone());
                user1.setUserEmail(user.getUserEmail());
                uService.updateUser(user1);
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
    public Map<String, Object> updateUserGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            map.put("status", 200);
            map.put("obj", uService.selectUserOneProjection(userid));

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 비밀번호 수정(userPw)
    @PostMapping(value = "/pwchange", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> pwchangeUser(@RequestHeader("token") String token, @RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user1 = uService.selectUserOne(userid);
            if (user.getUserId().equals(userid)) {
                if (user1.getUserPw().equals(user.getUserPw())) {
                    map.put("status", 100);
                } else {
                    user1.setUserPw(user.getUserPw());
                    uService.updateUser(user1);
                    map.put("status", 200);
                }
            } else {
                // User가 서로 같지 않을 경우 오류 반환
                map.put("status", "적합한 권한을 가지고 있지 않습니다");
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
                // 비밀번호가 틀릴 경우 오류 반환
                map.put("status", "비밀번호가 틀렸습니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }

        return map;
    }

}