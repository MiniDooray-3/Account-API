package com.nhnacademy.edu.minidooray.AccountApi.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class CreateUserRequest {
    @NotBlank
    private String id;
    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

}
