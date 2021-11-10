package com.team.security;

import com.team.service.SellerDetailService;
import com.team.service.UserDetailServiceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider{

    @Autowired
	private UserDetailServiceimpl uService;

    @Autowired
    private SellerDetailService sService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        System.out.println("--------CustomAuthenticationProvider---------");
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		System.out.println("----------CUSTOM ID--------" + username);
        System.out.println("----------CUSTOM PASSWORD--------" + password);
		System.out.println("----------CUSTOM AUTH--------" + authentication.toString());

		String roles = authentication.getAuthorities().iterator().next().toString();
        if(roles.equals("SELLER")){
            UserDetails accountContext = sService.loadUserByUsername(username);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (!passwordEncoder.matches(password, accountContext.getPassword())) {
                throw new BadCredentialsException("BadCredentialsException");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    accountContext, null, accountContext.getAuthorities());
            return authenticationToken;
        }  
        
        // roles가 USER일때
        UserDetails accountContext = uService.loadUserByUsername(username);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (!passwordEncoder.matches(password, accountContext.getPassword())) {
                throw new BadCredentialsException("BadCredentialsException");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    accountContext, null, accountContext.getAuthorities());
            return authenticationToken;
        }
    
    

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
}
