package com.muchatlu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserExistsException extends RuntimeException{

    public UserExistsException(){
        super();
    }

    public UserExistsException(String message){
        super(message);
    }

}
