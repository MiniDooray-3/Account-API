package com.nhnacademy.edu.minidooray.AccountApi.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.edu.minidooray.AccountApi.domain.User;
import com.nhnacademy.edu.minidooray.AccountApi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.AccountApi.model.request.LoginUserRequest;
import com.nhnacademy.edu.minidooray.AccountApi.repository.UserRepository;
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
    private UserRepository userRepository;

    @Test
    @DisplayName("로그인 성공")
    void testLoginSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginUserRequest loginUserRequest = new LoginUserRequest("test7", "1234");
        given(userRepository.findByIdAndPasswordAndStatus("test7", "1234", "가입")).willReturn(
                Optional.of(new User("test7", "test@test.com", "1234", "가입", null)));

        mockMvc.perform(post("/api/accounts/login")
                        .content(objectMapper.writeValueAsString(loginUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("해당하는 유저가 없어서 로그인 실패")
    void testLoginFailByNotMatch() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginUserRequest loginUserRequest = new LoginUserRequest("test", "1234");
        given(userRepository.findByIdAndPasswordAndStatus("test", "1234", "가입")).willReturn(Optional.empty());

        mockMvc.perform(post("/api/accounts/login")
                        .content(objectMapper.writeValueAsString(loginUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("존재하지 않는 유저 입니다.")));
    }

    @Test
    @DisplayName("유효성 검사로 인한 로그인 실패")
    void testLoginFailByValidationException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginUserRequest loginUserRequest = new LoginUserRequest("test", "123");
        given(userRepository.findByIdAndPasswordAndStatus("test", "1234", "가입")).willReturn(Optional.empty());

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
        given(userRepository.findById("test")).willReturn(
                Optional.empty());

        mockMvc.perform(post("/api/accounts/signup")
                        .content(objectMapper.writeValueAsString(createUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("아이디 중복으로 회원가입 실패")
    void testCreateUserFailByUserExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateUserRequest createUserRequest = new CreateUserRequest("test", "1234", "test@test.com");
        given(userRepository.findById("test")).willReturn(
                Optional.of(new User("test", "test@test.com", "1234", "가입", null)));

        mockMvc.perform(post("/api/accounts/signup")
                        .content(objectMapper.writeValueAsString(createUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("이미 존재하는 유저 입니다.")));
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
    @DisplayName("회원탈퇴 성공")
    void testDeleteUserSuccess() throws Exception {
        given(userRepository.findById("test")).willReturn(
                Optional.of(new User("test", "test@test.com", "1234", "가입", null)));

        mockMvc.perform(delete("/api/accounts/{id}", "test"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원탈퇴 실패")
    void testDeleteUserFail() throws Exception {
        given(userRepository.findById("test")).willReturn(
                Optional.empty());

        mockMvc.perform(delete("/api/accounts/{id}", "test"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("존재하지 않는 유저 입니다.")));
    }

    @Test
    @DisplayName("유저 조회")
    void testhasAccount() throws Exception {
        given(userRepository.findById("test")).willReturn(Optional.empty());

        mockMvc.perform(get("/api/accounts/{id}", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hasAccount", equalTo(false)));
    }
}