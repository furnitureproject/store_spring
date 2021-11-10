package com.team.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.team.entity.Seller;
import com.team.entity.User;
import com.team.repository.SellerRepository;
import com.team.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailServiceimpl implements UserDetailsService {

    @Autowired
    UserRepository uRepository;

    @Autowired
    SellerRepository sRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // username 은 아이디

        Optional<User> oUser = uRepository.findById(username);
        Optional<Seller> oSeller = sRepository.findById(username);


        System.out.println("1."+oUser);
        System.out.println("2."+oSeller);
        System.out.println("3."+oUser.getClass().getName());
        System.out.println("4."+oSeller.getClass().getName());

        if (oSeller.isEmpty()) {
            String[] role = { oUser.get().getRole() };
            Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(role);

            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                    oUser.get().getUserId(), oUser.get().getUserPw(), roles);
            System.out.println(user);
            return user;
        } else if (oUser.isEmpty()) {
            String[] role = { oSeller.get().getRole() };
            Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(role);

            org.springframework.security.core.userdetails.User seller = new org.springframework.security.core.userdetails.User(
                    oSeller.get().getSellerId(), oSeller.get().getSellerPw(), roles);
            System.out.println(seller);
            return seller;
        } else {
            return null;
        }

        // 정상작동

        // Optional<User> oUser = uRepository.findById(username);

        // String[] role = {oUser.get().getRole()};
        // Collection<GrantedAuthority> roles =
        // AuthorityUtils.createAuthorityList(role);

        // org.springframework.security.core.userdetails.User seller = new
        // org.springframework.security.core.userdetails.User(oUser.get().getUserId(),
        // oUser.get().getUserPw(), roles);
        // System.out.println(seller);
        // return seller;

    }

    // public UserDetails loadSellerBySellername(String username) throws
    // UsernameNotFoundException {
    // Optional<Seller> oSeller = sRepository.findById(username);

    // String[] role = {oSeller.get().getRole()};
    // Collection<GrantedAuthority> roles =
    // AuthorityUtils.createAuthorityList(role);

    // org.springframework.security.core.userdetails.User seller = new
    // org.springframework.security.core.userdetails.User(oSeller.get().getSellerId(),
    // oSeller.get().getSellerPw(), roles);
    // System.out.println(seller);
    // return seller;
    // }

}
