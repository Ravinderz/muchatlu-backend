package com.muchatlu.service;

import com.muchatlu.model.AuthenticateToken;
import com.muchatlu.repository.AuthenticationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenService {

    @Autowired
    AuthenticationTokenRepository repo;

    public AuthenticateToken getAuthToken(String token){
        return repo.findByToken(token);
    }

    public AuthenticateToken saveToken(AuthenticateToken token){
        return repo.save(token);
    }

}
