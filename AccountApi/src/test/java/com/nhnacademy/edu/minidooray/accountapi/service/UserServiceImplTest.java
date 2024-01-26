package com.nhnacademy.edu.minidooray.accountapi.service;


import com.nhnacademy.edu.minidooray.accountapi.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserServiceImplTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("로그인 성공")
    void testLoginSuccess() {
    }

    @Test
    @DisplayName("해당하는 유저가 없어서 로그인 실패")
    void testLoginFailByUserNotExist() {

    }

    @Test
    @DisplayName("회원가입 성공")
    void testCreateUserSuccess() {
    }

    @Test
    void testDeleteUser() {
    }

    @Test
    void testUpdateInactiveUserStatus() {
    }

    @Test
    void testGetUser() {
    }
}