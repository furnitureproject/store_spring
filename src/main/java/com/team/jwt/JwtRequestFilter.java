package com.team.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team.service.SecurityUserDetailServiceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
    
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    SecurityUserDetailServiceimpl suService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String token = request.getHeader("token");
            String username = null;

            if(token != null){
                username = jwtUtil.extractUsername(token);
            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                
                UserDetails userDetails = suService.loadUserByUsername(username);

                if(jwtUtil.validateToken(token, userDetails.getUsername())){
                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(upat);
                }
            }
            filterChain.doFilter(request, response);
        }
        catch(Exception e){
            response.sendError(578, "토큰오류");
        }
    }
}
