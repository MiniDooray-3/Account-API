package com.nhnacademy.edu.minidooray.AccountApi.model.request;

import javax.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class LoginUserRequest {
    @NotBlank
    private String id;
    @NotBlank
    private String password;

}
