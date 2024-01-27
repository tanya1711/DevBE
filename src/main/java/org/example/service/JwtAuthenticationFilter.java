package org.example.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Component
@NonNullApi
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private DaoAuthenticationProvider authenticationProvider;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println(request.getRequestURI());
            System.out.println(request.getMethod());
            if(request.getRequestURI().equals("/authenticate") || request.getRequestURI().equals("/register")) {
                String username = request.getParameter("email");
                String password = request.getParameter("password");
                System.out.println(username + " " + password);
                if (!Objects.equals(request.getRequestURI(), "/register")) {
                    Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String token = "Bearer " + jwtTokenService.generateToken(username, password);
                    response.addHeader("Authorization", token);
                    System.out.println("context");
                    System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
                }
            }
            else {
                String token = request.getHeader("Authorization");
                if(jwtTokenService.isValidate(token)){
                    System.out.println("hey token is valid");
                    Claims claims = jwtTokenService.extractUserNamePasswordFromJwtToken(token);
                    Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(claims.get("username"), claims.get("password")));
                    System.out.println("auth obj "+authentication.getPrincipal().toString());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
                else {
                    System.out.println("sorry invalid token");

                }
            }

        } catch (BadCredentialsException e) {
            System.out.println("Invalid/UserName password");
        }

        filterChain.doFilter(request, response);

    }




}
