package com.team.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.team.entity.Seller;
import com.team.entity.User;
import com.team.jwt.JwtSellerUtil;
import com.team.jwt.JwtUserUtil;
import com.team.jwt.JwtUtil;
import com.team.service.SecurityUserDetailServiceimpl;
import com.team.service.SellerService;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    SellerService sellerService;

    // @Autowired
    // JwtUserUtil jwtUserUtil;

    // @Autowired
    // JwtSellerUtil jwtSellerUtil;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SecurityUserDetailServiceimpl sserviceimpl;

    @Autowired
    UserService uService;

    @PostMapping(value = "/userlogin", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userloginPost(@RequestBody User user) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            System.out.println("userlogin입니다");
            String[] userRoles = { user.getRole() };
            Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(userRoles);

            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPw(), roles));
            
            map.put("status", 200);
            map.put("token", jwtUtil.generateToken(user.getUserId()));
            map.put("role", "user");
            // map.put("token", jwtUtil.generateToken(user.getUserId(), user.getRole()));
        } 
        // catch (Exception e) {
        //     e.printStackTrace();
        //     map.put("error", e.hashCode());
        // }
        catch(DisabledException | LockedException | BadCredentialsException e){
            if (e.getClass().equals(BadCredentialsException.class)) {
                e.printStackTrace();
                map.put("error", e.hashCode());
                map.put("status", "invalid-password");
            } else if (e.getClass().equals(DisabledException.class)) {
                e.printStackTrace();
                map.put("error", e.hashCode());
                map.put("status", "disable");
            } else if (e.getClass().equals(LockedException.class)) {
                e.printStackTrace();
                map.put("error", e.hashCode());
                map.put("status", "locked");
            } else {
                e.printStackTrace();
                map.put("error", e.hashCode());
                map.put("status", "unknown");
            }
        }

        return map;
    }

    @PostMapping(value = "/userinsert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userinsertTest(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            user.setUserPw(bcpe.encode(user.getUserPw()));
            uService.insertUser(user);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/sellerlogin", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> sellerloginPost(@RequestBody Seller seller) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            System.out.println("sellerlogin입니다");
            String[] userRoles = { seller.getRole() };
            Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(userRoles);

            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(seller.getSellerId(), seller.getSellerPw(), roles));

            map.put("status", 200);
            map.put("token", jwtUtil.generateToken(seller.getSellerId()));
            map.put("role", "seller");
            // map.put("token", jwtUtil.generateToken(seller.getSellerId(), seller.getRole()));
        } 
        // catch (Exception e) {
        //     e.printStackTrace();
        //     map.put("error", e.hashCode());
        // }
        catch(DisabledException | LockedException | BadCredentialsException e){
            if (e.getClass().equals(BadCredentialsException.class)) {
                e.printStackTrace();
                map.put("error", e.hashCode());
                map.put("status", "invalid-password");
            } else if (e.getClass().equals(DisabledException.class)) {
                e.printStackTrace();
                map.put("error", e.hashCode());
                map.put("status", "disable");
            } else if (e.getClass().equals(LockedException.class)) {
                e.printStackTrace();
                map.put("error", e.hashCode());
                map.put("status", "locked");
            } else {
                e.printStackTrace();
                map.put("error", e.hashCode());
                map.put("status", "unknown");
            }
        }

        return map;
    }

}
