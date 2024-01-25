package com.nhnacademy.edu.minidooray.AccountApi.service;

import com.nhnacademy.edu.minidooray.AccountApi.domain.User;
import com.nhnacademy.edu.minidooray.AccountApi.exception.UserAlreadyExsitException;
import com.nhnacademy.edu.minidooray.AccountApi.exception.UserNotExistException;
import com.nhnacademy.edu.minidooray.AccountApi.model.request.CreateUserRequest;
import com.nhnacademy.edu.minidooray.AccountApi.model.request.LoginUserRequest;
import com.nhnacademy.edu.minidooray.AccountApi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public void login(LoginUserRequest userRequest) {
        userRepository.findByIdAndPassword(userRequest.getId(), userRequest.getPassword()).orElseThrow();
    }

    @Override
    @Transactional
    public void createUser(CreateUserRequest createUserRequest) {
        boolean present = userRepository.findById(createUserRequest.getId()).isPresent();
        if (present) {
            throw new UserAlreadyExsitException();
        }
        User user = new User(createUserRequest.getId(), createUserRequest.getEmail(), createUserRequest.getPassword(),
                null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        boolean before = userRepository.findById(id).isPresent();
        if (!before) {
            throw new UserNotExistException();
        }
        userRepository.deleteById(id);
    }
}
