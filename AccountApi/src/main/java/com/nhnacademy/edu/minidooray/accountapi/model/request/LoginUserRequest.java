package com.nhnacademy.edu.minidooray.accountapi.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginUserRequest {
    @NotBlank
    @Size(max = 10)
    private final String userId;

    @NotBlank
    @Size(min = 4, max = 20)
    private final String userPassword;

}
