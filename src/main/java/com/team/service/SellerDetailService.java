package com.team.service;

import java.util.Collection;
import java.util.Optional;

import com.team.entity.Seller;
import com.team.repository.SellerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("SellerDetailService")
public class SellerDetailService implements UserDetailsService{

    @Autowired
    SellerRepository sRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Seller> oSeller = sRepository.findById(username);

        String[] role = { oSeller.get().getRole() };
        // String role = oSeller.get().getRole();
        Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(role);

        org.springframework.security.core.userdetails.User seller = new org.springframework.security.core.userdetails.User(
                oSeller.get().getSellerId(), oSeller.get().getSellerPw(), roles);
        System.out.println(seller);
        return seller;
    }
    
}
