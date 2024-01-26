package com.nhnacademy.edu.minidooray.AccountApi.service;

import com.nhnacademy.edu.minidooray.AccountApi.domain.User;
import com.nhnacademy.edu.minidooray.AccountApi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.AccountApi.model.request.LoginUserRequest;
import java.util.Optional;

public interface UserService {

    void login(LoginUserRequest userRequest);

    void createUser(CreateUserRequest createUserRequest);

    void deleteUser(String id);

    void updateInactiveUserStatus();

    Optional<User> getUser(String id);
}
