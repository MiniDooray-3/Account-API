package com.nhnacademy.edu.minidooray.accountapi.controller;


import com.nhnacademy.edu.minidooray.accountapi.exception.ValidationFailedException;
import com.nhnacademy.edu.minidooray.accountapi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.accountapi.model.request.LoginUserRequest;
import com.nhnacademy.edu.minidooray.accountapi.model.response.HasAccountResponse;
import com.nhnacademy.edu.minidooray.accountapi.service.UserService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public void login(@Valid @RequestBody LoginUserRequest loginUserRequest,
                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        userService.login(loginUserRequest);
    }

    @PostMapping("/accounts/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody CreateUserRequest createUserRequest,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        userService.createUser(createUserRequest);
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }

    @GetMapping("/accounts/{id}")
    public HasAccountResponse hasAccount(@PathVariable("id") String id) {
        return new HasAccountResponse(userService.getUser(id).isPresent());
    }

}
