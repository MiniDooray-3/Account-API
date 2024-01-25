package com.nhnacademy.edu.minidooray.AccountApi.repository;


import com.nhnacademy.edu.minidooray.AccountApi.domain.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByIdAndPasswordAndStatus(String id, String password, String status);

    @Modifying
    @Query("UPDATE User s SET s.lastLogin = ?2 WHERE s.id = ?1")
    void updateLastLoginDate(String id, LocalDate localDate);

    @Modifying
    @Query("UPDATE User s SET s.status = ?2 WHERE s.id = ?1")
    void updateUserStatus(String id, String status);

    List<User> findByLastLoginBeforeAndStatus(LocalDate twoYearsAgo, String status);
}
