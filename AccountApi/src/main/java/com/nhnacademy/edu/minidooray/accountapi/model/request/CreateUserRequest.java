package com.nhnacademy.edu.minidooray.accountapi.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserRequest {
    @NotBlank
    @Size(max = 10)
    private final String id;

    @NotBlank
    @Size(min = 4, max = 20)
    private final String password;

    @NotBlank
    @Email
    @Size(max = 20)
    private final String email;

}
