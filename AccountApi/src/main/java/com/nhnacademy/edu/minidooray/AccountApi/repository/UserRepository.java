package com.nhnacademy.edu.minidooray.AccountApi.repository;


import com.nhnacademy.edu.minidooray.AccountApi.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByIdAndPassword(String id, String password);

}
