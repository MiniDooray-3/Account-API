package com.nhnacademy.edu.minidooray.accountapi.controller;


import com.nhnacademy.edu.minidooray.accountapi.exception.UserAlreadyExsitException;
import com.nhnacademy.edu.minidooray.accountapi.exception.UserNotExistException;
import com.nhnacademy.edu.minidooray.accountapi.exception.ValidationFailedException;
import com.nhnacademy.edu.minidooray.accountapi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.accountapi.model.request.LoginUserRequest;
import com.nhnacademy.edu.minidooray.accountapi.model.response.hasAccountResponse;
import com.nhnacademy.edu.minidooray.accountapi.service.UserService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public hasAccountResponse hasAccount(@PathVariable("id") String id) {
        return new hasAccountResponse(userService.getUser(id).isPresent());
    }

    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleUsernameNotFoundException(UserNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExsitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExsitException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationFailedException(ValidationFailedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}