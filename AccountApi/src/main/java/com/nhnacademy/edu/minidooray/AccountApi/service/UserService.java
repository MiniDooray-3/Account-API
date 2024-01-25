package com.nhnacademy.edu.minidooray.AccountApi.service;

import com.nhnacademy.edu.minidooray.AccountApi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.AccountApi.model.request.LoginUserRequest;

public interface UserService {

    void login(LoginUserRequest userRequest);

    void createUser(CreateUserRequest createUserRequest);

    void deleteUser(String id);

    void updateInactiveUserStatus();

}
