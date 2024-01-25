package com.nhnacademy.edu.minidooray.AccountApi.exception;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException() {
        super("존재하지 않는 유저 입니다.");
    }
}
