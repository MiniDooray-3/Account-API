package com.nhnacademy.edu.minidooray.accountapi.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.edu.minidooray.accountapi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.accountapi.repository.UserRepository;
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
class WebControllerAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("UserNotFound 발생시 UNAUTHORIZED 반환 테스트")
    void handleUsernameNotFoundException() throws Exception {
        given(userRepository.findById("test")).willReturn(
                Optional.empty());

        mockMvc.perform(delete("/api/accounts/{id}", "test"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("존재하지 않는 유저 입니다.")));
    }

    @Test
    @DisplayName("UserAlreadyExist 발생시 BAD_REQUEST 반환 테스트")
    void handleUserAlreadyExistException() throws Exception {
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
}