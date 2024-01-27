package com.nhnacademy.edu.minidooray.accountapi.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("로그인 실패");
    }
}
