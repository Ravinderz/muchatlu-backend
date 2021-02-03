package com.muchatlu.model;

import com.muchatlu.repository.AuthenticationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuthenticateToken {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String token;
    private Boolean isActive;
    private String type;
    private String tokenId;

    public AuthenticateToken(String token,Boolean isActive,String type,String tokenId){
        this.token = token;
        this.isActive = isActive;
        this.type = type;
        this.tokenId = tokenId;
    }


}
