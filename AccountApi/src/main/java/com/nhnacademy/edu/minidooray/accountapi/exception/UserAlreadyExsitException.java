package com.nhnacademy.edu.minidooray.accountapi.exception;

public class UserAlreadyExsitException extends RuntimeException {
    public UserAlreadyExsitException() {
        super("이미 존재하는 유저 입니다.");
    }
}
