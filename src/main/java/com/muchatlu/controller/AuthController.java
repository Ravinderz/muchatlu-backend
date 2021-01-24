package com.muchatlu.controller;

import com.muchatlu.model.*;
import com.muchatlu.service.AuthenticationTokenService;
import com.muchatlu.service.MyUserDetailsService;
import com.muchatlu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationTokenService authTokenService;

    @PostMapping("/authenticate")
    public AuthResponse getToken(@RequestBody AuthRequest request) throws Exception {

        Authentication principle = null;
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        }catch(Exception e) {
            throw new Exception("Incorrect username and password");
        }

        UserDetails details = userDetailsService.loadUserByUsername(request.getEmail());
        MyUserDetails user = (MyUserDetails) details;
        String token = jwtUtil.generateToken(user);
        AuthenticateToken authToken = new AuthenticateToken(token,true);
        System.out.println(authToken);
        authTokenService.saveToken(authToken);
        AuthResponse response = new AuthResponse(user.getId(),token);
        return response;

    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
