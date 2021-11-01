package com.team.security;

import com.team.jwt.JwtRequestFilter;
import com.team.service.SellerDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;


// @EnableWebSecurity
// @RequiredArgsConstructor
// @Order(1)
public class SellerSecurityConfig{
// public class SellerSecurityConfig extends WebSecurityConfigurerAdapter{
    
    // @Autowired
    // SellerDetailService sService;

    // @Bean
    // public BCryptPasswordEncoder encode(){
    //     return new BCryptPasswordEncoder();
    // }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.userDetailsService(sService).passwordEncoder(encode());
    // }

    // @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    // @Override
    // public AuthenticationManager authenticationManagerBean() throws Exception {
    //     return super.authenticationManagerBean();
    // }

    // @Autowired
    // private JwtRequestFilter jwtRequestFilter;


    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.authorizeRequests()
    //     .antMatchers("/admin", "/admin/*", "/api/admin/*").hasAnyRole("ADMIN")
    //     .anyRequest().permitAll();

    //     http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    //     http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    //     http.csrf().disable();
    //     http.headers().frameOptions().sameOrigin();

    // }

}
