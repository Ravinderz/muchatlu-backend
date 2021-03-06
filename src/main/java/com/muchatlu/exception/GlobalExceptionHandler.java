package com.muchatlu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Component
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse err = new ErrorResponse(ex.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse err = new ErrorResponse(ex.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserExistsException.class)
    public final ResponseEntity<Object> handleUserExistsException(Exception ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse err = new ErrorResponse(ex.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public final ResponseEntity<Object> handleNotAuthorizedException(Exception ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse err = new ErrorResponse(ex.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidSessionException.class)
    public final ResponseEntity<Object> handleInvalidSessionException(Exception ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse err = new ErrorResponse(ex.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserIsAlreadyFriendException.class)
    public final ResponseEntity<Object> handleUserIsAlreadyFriendException(Exception ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse err = new ErrorResponse(ex.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserDoesntExistException.class)
    public final ResponseEntity<Object> handleUserDoesntExistException(Exception ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse err = new ErrorResponse(ex.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JwtExpiredException.class)
    public final ResponseEntity<Object> handleJwtExpiredException(Exception ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse err = new ErrorResponse(ex.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
