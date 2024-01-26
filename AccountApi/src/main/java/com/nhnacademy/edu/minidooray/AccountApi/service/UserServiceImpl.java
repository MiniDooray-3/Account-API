package com.nhnacademy.edu.minidooray.AccountApi.service;

import com.nhnacademy.edu.minidooray.AccountApi.domain.User;
import com.nhnacademy.edu.minidooray.AccountApi.exception.UserAlreadyExsitException;
import com.nhnacademy.edu.minidooray.AccountApi.exception.UserNotExistException;
import com.nhnacademy.edu.minidooray.AccountApi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.AccountApi.model.request.LoginUserRequest;
import com.nhnacademy.edu.minidooray.AccountApi.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void login(LoginUserRequest userRequest) {
        userRepository.findByIdAndPasswordAndStatus(userRequest.getId(), userRequest.getPassword(), "가입").orElseThrow(
                UserNotExistException::new);
        userRepository.updateLastLoginDate(userRequest.getId(), LocalDate.now());
    }

    @Override
    @Transactional
    public void createUser(CreateUserRequest createUserRequest) {
        boolean present = userRepository.findById(createUserRequest.getId()).isPresent();
        if (present) {
            throw new UserAlreadyExsitException();
        }
        User user = new User(createUserRequest.getId(), createUserRequest.getEmail(), createUserRequest.getPassword(),
                "가입", null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        boolean before = userRepository.findById(id).isPresent();
        if (!before) {
            throw new UserNotExistException();
        }
        userRepository.updateUserStatus(id, "탈퇴");
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateInactiveUserStatus() {
        LocalDate twoYearsAgo = LocalDate.now().minusYears(2);
        List<User> inactiveUsers = userRepository.findByLastLoginBeforeAndStatus(twoYearsAgo, "가입");

        for (User user : inactiveUsers) {
            userRepository.updateUserStatus(user.getId(), "휴면");
        }
    }

    @Override
    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }
}
