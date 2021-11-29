package com.team.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import com.team.aes.AesCording;
import com.team.entity.User;
import com.team.enums.Status;
import com.team.jwt.JwtUtil;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService uService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SpringTemplateEngine templateEngine;

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
        map.put("status", Status.COMPLETE.getCode());
        return map;
    }

    // 회원 가입(User entity 값)
    @PostMapping(value = "/join", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> joinUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println(user);
            if (uService.selectUserOne(user.getUserId()) != null) {
                map.put("status", "같은 아이디가 존재하고 있습니다");
            } else if (uService.selectUserByEmail(user.getUserEmail()) != null) {
                map.put("status", "같은 이메일이 존재하고 있습니다");
            } else if (uService.selectUserByPhone(user.getUserPhone()) != null) {
                map.put("status", "같은 전화번호가 존재하고 있습니다");
            } else {
                BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
                user.setUserPw(bcpe.encode(user.getUserPw()));
                // TEST용 추가
                String phone = user.getUserPhone();
                System.out.println(phone);
                // String newphone = phone.toString().replaceAll("-", "");
                // user.setUserPhone(phone);

                uService.insertUser(user);
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 아이디 찾기
    @GetMapping(value = "/unknownid")
    public Map<String, Object> selectUserGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.COMPLETE.getCode());
        return map;
    }

    // 아이디 찾기()
    @PostMapping(value = "/unknownid", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            String name = user.getUserName();
            String email = user.getUserEmail();
            String phone = user.getUserPhone();
            // String phone1 = phone.toString().replaceAll("-", "");
            if (uService.selectUserByEmail(email).getuserName().equals(name)) {
                String userid = uService.selectUserByEmail(email).getuserId();
                map.put("status", Status.COMPLETE.getCode());
                map.put("userid", userid);
            } else if (uService.selectUserByPhone(phone).getuserName().equals(name)) {
                String userid = uService.selectUserByPhone(phone).getuserId();
                map.put("status", Status.COMPLETE.getCode());
                map.put("userid", userid);
            } else {
                map.put("status", Status.ERROR.getStatus());
            }
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
        map.put("status", Status.COMPLETE.getCode());
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
                map.put("status", Status.COMPLETE.getCode());
            } else {
                map.put("status", Status.UNUSER.getStatus());
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
                map.put("status", Status.COMPLETE.getCode());
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
            map.put("status", Status.COMPLETE.getCode());
            map.put("obj", uService.selectUserOneProjection(userid));

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 비밀번호 수정(userPw)
    // 아직은 로그인 한 상태에서만 가능 비밀번호 분실 시 사용 불가
    @PostMapping(value = "/pwchange", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> pwchangeUser(@RequestHeader("token") String token, @RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user1 = uService.selectUserOne(userid);
            if (user.getUserId().equals(userid)) {
                if (user1.getUserPw().equals(user.getUserPw())) {
                    map.put("status", "현재와 같은 비밀번호입니다");
                } else {
                    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
                    user1.setUserPw(bcpe.encode(user.getUserPw()));
                    uService.updateUser(user1);
                    map.put("status", Status.COMPLETE.getCode());
                }
            } else {
                // User가 서로 같지 않을 경우 오류 반환
                map.put("status", Status.ERROR.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }

        return map;
    }

    // 비밀번호 확인 수정 email 보내기
    @PostMapping(value = "/unknownpw", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> unknownPwEmail(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = user.getUserId();
            String email = user.getUserEmail();
            if (uService.selectUserOne(userid).getUserEmail().equals(email)) {
                AesCording aesCording = new AesCording();
                String encordtext = aesCording.encrypt(email);

                Map<String, Object> values = new HashMap<>();
                values.put("email", encordtext);
                values.put("userid", uService.selectUserByEmail(email).getuserId());
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                // 메일 제목 설정
                String title = "비밀번호 변경 요청입니다";
                helper.setSubject(title);
                // 수신자 설정
                helper.setTo(email);
                // 템플릿에 전달할 데이터 설정
                Context context = new Context();
                values.forEach((key, value) -> {
                    context.setVariable(key, value);
                });
                // 메일 내용 설정 : 템플릿 프로세스
                String mail = "mail";
                String jsp = templateEngine.process(mail, context);
                helper.setText(jsp, true);
                javaMailSender.send(message);
                // USER PWCHANGECHECK 변경
                User user1 = uService.selectUserOne(userid);
                user1.setPwChangeCheck(1);
                uService.updateUser(user1);
                map.put("status", Status.COMPLETE.getCode());
            } else {
                map.put("status", "잘못된 이메일입니다");
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
                map.put("status", Status.COMPLETE.getCode());
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