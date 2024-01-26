package com.nhnacademy.edu.minidooray.AccountApi.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Value;

@Value
public class LoginUserRequest {
    @NotBlank
    @Size(max = 10)
    private String userId;

    @NotBlank
    @Size(min = 4, max = 20)
    private String userPassword;

}
