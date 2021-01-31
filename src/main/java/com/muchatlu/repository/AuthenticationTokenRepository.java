package com.muchatlu.repository;

import com.muchatlu.model.AuthenticateToken;
import com.muchatlu.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticateToken, Long>{


    public AuthenticateToken findByToken(String token);

    public AuthenticateToken findByTokenId(String id);

}
