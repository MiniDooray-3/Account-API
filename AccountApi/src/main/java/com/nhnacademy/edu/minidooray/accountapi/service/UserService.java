package com.nhnacademy.edu.minidooray.accountapi.service;

import com.nhnacademy.edu.minidooray.accountapi.domain.User;
import com.nhnacademy.edu.minidooray.accountapi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.accountapi.model.request.LoginUserRequest;
import java.util.Optional;

public interface UserService {

    void login(LoginUserRequest userRequest);

    void createUser(CreateUserRequest createUserRequest);

    void deleteUser(String id);

    void updateInactiveUserStatus();

    Optional<User> getUser(String id);
}
