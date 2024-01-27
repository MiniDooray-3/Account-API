package com.nhnacademy.edu.minidooray.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.edu.minidooray.accountapi.domain.User;
import com.nhnacademy.edu.minidooray.accountapi.model.response.UserResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    @Autowired
    TestEntityManager entityManager;


    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Save와 FindById를 함께 테스트")
    void testSaveAndFindById() {
        User user = new User("testUser", "test@test.com", "1234", null, LocalDate.now());

        entityManager.merge(user);

        User result = userRepository.findById("testUser").orElse(null);
        assertThat(Objects.requireNonNull(result).getId()).isEqualTo("testUser");
    }

    @Test
    void testFindByIdAndPasswordAndStatus() {
        User user = new User("testUser", "test@test.com", "1234", "가입", LocalDate.now());

        entityManager.merge(user);

        UserResponse result = userRepository.findByIdAndPasswordAndStatus("testUser", "1234", "가입").orElse(null);
        assertThat(Objects.requireNonNull(result).getId()).isEqualTo("testUser");
    }

    @Test
    void testUpdateLastLoginDate() {
        User user = new User("testUser", "test@test.com", "1234", "가입", LocalDate.now());

        entityManager.merge(user);
        LocalDate localDate = LocalDate.parse("2022-12-31");
        userRepository.updateLastLoginDate("testUser", localDate);
        entityManager.clear();

        User result = userRepository.findById("testUser").orElse(null);
        assertThat(result.getLastLogin()).isEqualTo(localDate);
    }

    @Test
    void testUpdateUserStatus() {
        User user = new User("testUser", "test@test.com", "1234", "가입", LocalDate.now());

        entityManager.merge(user);
        userRepository.updateUserStatus("testUser", "탈퇴");
        entityManager.clear();

        User result = userRepository.findById("testUser").orElse(null);
        assertThat(result.getStatus()).isEqualTo("탈퇴");
    }

    @Test
    void testFindByLastLoginBeforeAndStatus() {
        LocalDate localDate = LocalDate.parse("2020-12-21");
        User user = new User("testUser", "test@test.com", "1234", "가입", localDate);

        entityManager.merge(user);

        LocalDate twoYearsAgo = LocalDate.now().minusYears(2);
        List<UserResponse> users = userRepository.findByLastLoginBeforeAndStatus(twoYearsAgo, "가입");
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getId()).isEqualTo("testUser");
    }
}