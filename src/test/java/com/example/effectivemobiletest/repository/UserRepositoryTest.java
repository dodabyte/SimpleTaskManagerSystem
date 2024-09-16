package com.example.effectivemobiletest.repository;

import com.example.effectivemobiletest.config.TestContainersConfiguration;
import com.example.effectivemobiletest.data.TestDataFactory;
import com.example.effectivemobiletest.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@DataJpaTest
@Import(TestContainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/test-data.sql")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername_whenFindIsOk_thenReturnOptionalUser() {
        User testUser = TestDataFactory.createUser2();

        Optional<User> user = userRepository.findByUsername(testUser.getUsername());

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(user.get(), testUser);
    }

    @Test
    public void findByUsername_whenNotFound_thenReturnEmptyOptionalUser() {
        Optional<User> user = userRepository.findByUsername("blablatest");

        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    public void findByEmail_whenFindIsOk_thenReturnOptionalUser() {
        User testUser = TestDataFactory.createUser2();

        Optional<User> user = userRepository.findByEmail(testUser.getEmail());

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(user.get(), testUser);
    }

    @Test
    public void findByEmail_whenNotFound_thenReturnEmptyOptionalUser() {
        Optional<User> user = userRepository.findByUsername("blabla@mail.ru");

        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    public void existsByUsernameOrEmail_whenFindIsOk_thenReturnTrue() {
        User testUser = TestDataFactory.createUser2();

        boolean result = userRepository.existsByUsernameOrEmail(testUser.getUsername(), testUser.getEmail());

        Assertions.assertTrue(result);
    }

    @Test
    public void existsByUsernameOrEmail_whenNotFound_thenReturnFalse() {
        boolean result = userRepository.existsByUsernameOrEmail("blablatest", "blabla@mail.ru");

        Assertions.assertFalse(result);
    }
}