package com.nhnacademy.edu.minidooray.accountapi.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Value;

@Value
public class CreateUserRequest {
    @NotBlank
    @Size(max = 10)
    private String id;

    @NotBlank
    @Size(min = 4, max = 20)
    private String password;

    @NotBlank
    @Email
    @Size(max = 20)
    private String email;

}
