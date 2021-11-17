package com.team.service;

import java.util.Collection;
import java.util.Optional;

import com.team.entity.User;
import com.team.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("UserDetailServiceimpl")
public class UserDetailServiceimpl implements UserDetailsService {

    @Autowired
    UserRepository uRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> oUser = uRepository.findById(username);

        String[] role = { oUser.get().getRole() };
        Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(role);

        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                oUser.get().getUserId(), oUser.get().getUserPw(), roles);
        System.out.println(user);
        return user;
    }

}
