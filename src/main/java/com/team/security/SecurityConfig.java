package com.team.security;

import javax.annotation.PostConstruct;

import com.team.jwt.JwtRequestFilter;
import com.team.service.SecurityUserDetailServiceimpl;
import com.team.service.SellerDetailService;
import com.team.service.UserDetailServiceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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

// 1
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    SecurityUserDetailServiceimpl sudService;

    @Bean
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sudService).passwordEncoder(encode());
    }

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/admin", "/admin/*", "/api/admin/*").hasAnyRole("ADMIN")
        .anyRequest().permitAll();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

    }
}


// 2
// @Configuration
// @EnableWebSecurity
// @Order(2)
// public class SecurityConfig extends WebSecurityConfigurerAdapter{

//     @Autowired
//     UserDetailServiceimpl uService;

//     @Bean
//     public BCryptPasswordEncoder encode(){
//         return new BCryptPasswordEncoder();
//     }

//     @Override
//     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//         auth.userDetailsService(uService).passwordEncoder(encode());
//     }

//     @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//     @Override
//     public AuthenticationManager authenticationManagerBean() throws Exception {
//         return super.authenticationManagerBean();
//     }

//     @Autowired
//     private JwtRequestFilter jwtRequestFilter;


//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.authorizeRequests()
//         .antMatchers("/admin", "/admin/*", "/api/admin/*").hasAnyRole("ADMIN")
//         .anyRequest().permitAll();

//         http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

//         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//         http.csrf().disable();
//         http.headers().frameOptions().sameOrigin();

//     }

// }

// @Configuration
// @Order(1)
// class SecondSecurityConfig extends WebSecurityConfigurerAdapter{
    
//     @Autowired
//     SellerDetailService sService;

//     @Bean
//     public BCryptPasswordEncoder encode(){
//         return new BCryptPasswordEncoder();
//     }

//     @Override
//     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//         auth.userDetailsService(sService).passwordEncoder(encode());
//     }

//     @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//     @Override
//     public AuthenticationManager authenticationManagerBean() throws Exception {
//         return super.authenticationManagerBean();
//     }

//     @Autowired
//     private JwtRequestFilter jwtRequestFilter;


//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.authorizeRequests()
//         .antMatchers("/admin", "/admin/*", "/api/admin/*").hasAnyRole("ADMIN")
//         .anyRequest().permitAll();

//         http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

//         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//         http.csrf().disable();
//         http.headers().frameOptions().sameOrigin();

//     }
// }




// 3
// @Configuration
// @EnableWebSecurity
// public class SecurityConfig{

    
//     @Configuration
//     public static class FirstUserConfig extends WebSecurityConfigurerAdapter{

//         @Autowired
//         UserDetailServiceimpl uService;

//         @Bean
//         public BCryptPasswordEncoder encode(){
//             return new BCryptPasswordEncoder();
//         }

//         @Override
//         protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//             System.out.println("uService 가능");
//             auth.userDetailsService(uService).passwordEncoder(encode());
//         }
//         @Primary //이거 붙으면 작동하고 안하면 작동 안함
//         @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//         @Override
//         public AuthenticationManager authenticationManagerBean() throws Exception {
//             return super.authenticationManagerBean();
//         }

//         @Autowired
//         private JwtRequestFilter jwtRequestFilter;


//         @Override
//         protected void configure(HttpSecurity http) throws Exception {
//             http.authorizeRequests()
//             // .antMatchers("/xxx", "/xexe/*", "/oxox/admin/*").hasAnyRole("XOXO")
//             .anyRequest().permitAll();

//             http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

//             http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//             http.csrf().disable();
//             http.headers().frameOptions().sameOrigin();

//         }
//     }
//     @Order(1)
//     @Configuration
//     public static class SecondSellerConfig extends WebSecurityConfigurerAdapter{

        
//         @Autowired
//         SellerDetailService sService;

//         @Bean
//         public BCryptPasswordEncoder encode(){
//             return new BCryptPasswordEncoder();
//         }

//         @Override
//         protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//             System.out.println("sService 가능");
//             auth.userDetailsService(sService).passwordEncoder(encode());
//         }

        
//         @Bean(name = "test")
//         @Override
//         public AuthenticationManager authenticationManagerBean() throws Exception {
//             return super.authenticationManagerBean();
//         }


//         @Autowired
//         private JwtRequestFilter jwtRequestFilter;


//         @Override
//         protected void configure(HttpSecurity http) throws Exception {
//             http.authorizeRequests()
//             .antMatchers("/seller", "/seller/*").hasAnyRole("SELLER")
//             .anyRequest().permitAll();

//             http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

//             http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//             http.csrf().disable();
//             http.headers().frameOptions().sameOrigin();

//         }
//     }
    
// }