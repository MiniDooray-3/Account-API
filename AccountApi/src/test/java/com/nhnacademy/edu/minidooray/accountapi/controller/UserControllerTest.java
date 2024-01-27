package com.nhnacademy.edu.minidooray.accountapi.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.edu.minidooray.accountapi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.accountapi.model.request.LoginUserRequest;
import com.nhnacademy.edu.minidooray.accountapi.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("로그인 성공")
    void testLoginSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginUserRequest loginUserRequest = new LoginUserRequest("test", "1234");
        doNothing().when(userService).login(loginUserRequest);

        mockMvc.perform(post("/api/accounts/login")
                        .content(objectMapper.writeValueAsString(loginUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).login(any(LoginUserRequest.class));
    }

    @Test
    @DisplayName("유효성 검사로 인한 로그인 실패")
    void testLoginFail() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginUserRequest loginUserRequest = new LoginUserRequest("test", "123");

        mockMvc.perform(post("/api/accounts/login")
                        .content(objectMapper.writeValueAsString(loginUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("ObjectName=")))
                .andExpect(content().string(containsString(",Message=")))
                .andExpect(content().string(containsString(",code=")));

    }

    @Test
    @DisplayName("회원가입 성공")
    void testCreateUserSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateUserRequest createUserRequest = new CreateUserRequest("test", "1234", "test@test.com");
        doNothing().when(userService).createUser(createUserRequest);

        mockMvc.perform(post("/api/accounts/signup")
                        .content(objectMapper.writeValueAsString(createUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(userService).createUser(any(CreateUserRequest.class));
    }

    @Test
    @DisplayName("유효성 검사로 인한 회원가입 실패")
    void testCreateUserFailByValidationException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateUserRequest createUserRequest = new CreateUserRequest("test", "123", "test@test.com");

        mockMvc.perform(post("/api/accounts/signup")
                        .content(objectMapper.writeValueAsString(createUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("ObjectName=")))
                .andExpect(content().string(containsString(",Message=")))
                .andExpect(content().string(containsString(",code=")));
    }

    @Test
    @DisplayName("회원탈퇴")
    void testDeleteUserSuccess() throws Exception {
        doNothing().when(userService).deleteUser(any(String.class));

        mockMvc.perform(delete("/api/accounts/{id}", "test"))
                .andExpect(status().isOk());

        verify(userService).deleteUser(any(String.class));
    }

    @Test
    @DisplayName("유저 조회")
    void testHasAccount() throws Exception {
        given(userService.getUser("test")).willReturn(Optional.empty());

        mockMvc.perform(get("/api/accounts/{id}", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hasAccount", equalTo(false)));

        verify(userService).getUser(any(String.class));
    }

}