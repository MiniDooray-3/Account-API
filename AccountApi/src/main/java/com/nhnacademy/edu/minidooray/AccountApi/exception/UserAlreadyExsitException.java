package com.nhnacademy.edu.minidooray.AccountApi.exception;

public class UserAlreadyExsitException extends RuntimeException {
    public UserAlreadyExsitException() {
        super("이미 존재하는 유저 입니다.");
    }
}
