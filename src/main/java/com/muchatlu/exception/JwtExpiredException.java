package com.muchatlu.exception;

import org.omg.SendingContext.RunTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class JwtExpiredException extends RuntimeException{

    public JwtExpiredException(String message){
        super(message);
    }

}
