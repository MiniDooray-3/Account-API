package com.nhnacademy.edu.minidooray.accountapi.controller;

import com.nhnacademy.edu.minidooray.accountapi.exception.LoginFailedException;
import com.nhnacademy.edu.minidooray.accountapi.exception.UserAlreadyExsitException;
import com.nhnacademy.edu.minidooray.accountapi.exception.UserNotExistException;
import com.nhnacademy.edu.minidooray.accountapi.exception.ValidationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WebControllerAdvice {

    @ExceptionHandler({UserNotExistException.class, UserAlreadyExsitException.class, ValidationFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUsernameNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleLoginFailedException(UserNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
