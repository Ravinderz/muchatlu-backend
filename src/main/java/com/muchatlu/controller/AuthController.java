package com.muchatlu.controller;

import com.muchatlu.model.MyUserDetails;
import com.muchatlu.model.Person;
import com.muchatlu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public String getToken(@RequestBody Person person) throws Exception {
        Authentication principle = null;
        try {
            principle = authManager.authenticate(new UsernamePasswordAuthenticationToken(person.getEmail(),person.getPassword()));
        }catch(Exception e) {
            throw new Exception("Incorrect username and password");
        }

        MyUserDetails details = (MyUserDetails) principle.getPrincipal();

        final String token = jwtUtil.generateToken(details);
        return token;
    }
}
