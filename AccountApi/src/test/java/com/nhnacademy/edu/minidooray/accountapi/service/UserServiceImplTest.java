package com.nhnacademy.edu.minidooray.accountapi.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.edu.minidooray.accountapi.domain.User;
import com.nhnacademy.edu.minidooray.accountapi.exception.UserAlreadyExsitException;
import com.nhnacademy.edu.minidooray.accountapi.exception.UserNotExistException;
import com.nhnacademy.edu.minidooray.accountapi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.accountapi.model.request.LoginUserRequest;
import com.nhnacademy.edu.minidooray.accountapi.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("로그인 성공")
    void testLoginSuccess() {
        User test = new User("test", "test@test.com", "1234", "가입", null);
        given(userRepository.findByIdAndPasswordAndStatus("test", "1234", "가입")).willReturn(
                Optional.of(test));
        doNothing().when(userRepository).updateLastLoginDate(any(String.class), any(LocalDate.class));


        userService.login(new LoginUserRequest("test", "1234"));
        verify(userRepository).findByIdAndPasswordAndStatus("test", "1234", "가입");
        verify(userRepository).updateLastLoginDate("test", LocalDate.now());
    }

    @Test
    @DisplayName("해당하는 유저가 없어서 로그인 실패")
    void testLoginFailByUserNotExist() {
        given(userRepository.findByIdAndPasswordAndStatus("test", "1234", "가입")).willReturn(
                Optional.empty());
        LoginUserRequest loginUserRequest = new LoginUserRequest("test", "1234");


        Assertions.assertThrows(UserNotExistException.class,
                () -> userService.login(loginUserRequest));
    }

    @Test
    @DisplayName("회원가입 성공")
    void testCreateUserSuccess() {
        User test = new User("test", "test@test.com", "1234", "가입", null);
        given(userRepository.findById("test")).willReturn(Optional.empty());
        given(userRepository.save(test)).willReturn(null);

        userService.createUser(new CreateUserRequest("test", "1234", "test@test.com"));
        verify(userRepository).findById("test");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("아이디 중복으로 회원가입 실패")
    void testCreateUserFailByUserExist() {
        User test = new User("test", "test@test.com", "1234", "가입", null);
        given(userRepository.findById("test")).willReturn(Optional.of(test));
        CreateUserRequest createUserRequest = new CreateUserRequest("test", "1234", "test@test.com");

        Assertions.assertThrows(UserAlreadyExsitException.class,
                () -> userService.createUser(createUserRequest));
    }

    @Test
    @DisplayName("회원탈퇴 성공")
    void testDeleteUserSuccess() {
        User test = new User("test", "test@test.com", "1234", "가입", null);
        given(userRepository.findById("test")).willReturn(Optional.of(test));
        doNothing().when(userRepository).updateUserStatus("test", "탈퇴");

        userService.deleteUser("test");
        verify(userRepository).findById("test");
        verify(userRepository).updateUserStatus("test", "탈퇴");
    }

    @Test
    @DisplayName("해당하는 유저가 없어서 회원탈퇴 실패")
    void testDeleteUserFailByUserNotExist() {
        given(userRepository.findById("test")).willReturn(Optional.empty());

        Assertions.assertThrows(UserNotExistException.class,
                () -> userService.deleteUser("test"));
    }

    @Test
    @DisplayName("마지막 로그인날로부터 2년 이후 휴면 계정 전환")
    void testUpdateInactiveUserStatus() {
        User test1 = new User("test", "test@test.com", "1234", "가입", null);
        User test2 = new User("test", "test@test.com", "1234", "가입", null);
        given(userRepository.findByLastLoginBeforeAndStatus(any(LocalDate.class), any(String.class))).willReturn(
                List.of(test1, test2));
        doNothing().when(userRepository).updateUserStatus("test", "휴면");

        userService.updateInactiveUserStatus();
        verify(userRepository, times(2)).updateUserStatus(any(String.class), any(String.class));
    }

    @Test
    @DisplayName("유저 조회")
    void testGetUser() {
        given(userRepository.findById("test")).willReturn(Optional.empty());

        userService.getUser("test");
        verify(userRepository, times(1)).findById("test");
    }
}