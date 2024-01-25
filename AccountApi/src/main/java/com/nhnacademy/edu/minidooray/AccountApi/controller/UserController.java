package com.nhnacademy.edu.minidooray.AccountApi.controller;


import com.nhnacademy.edu.minidooray.AccountApi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.AccountApi.model.request.LoginUserRequest;
import com.nhnacademy.edu.minidooray.AccountApi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/accounts/login")
    public void login(@RequestBody LoginUserRequest loginUserRequest) {
        userService.login(loginUserRequest);
    }

    @PostMapping("/accounts/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody CreateUserRequest createUserRequest) {
        userService.createUser(createUserRequest);
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }
}
