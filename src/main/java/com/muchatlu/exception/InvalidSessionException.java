package com.muchatlu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidSessionException extends RuntimeException{

    public InvalidSessionException(){
        super();
    }

    public InvalidSessionException(String message){
        super(message);
    }

}
