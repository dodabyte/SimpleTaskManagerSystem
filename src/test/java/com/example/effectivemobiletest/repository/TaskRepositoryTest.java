package com.example.effectivemobiletest.repository;

import com.example.effectivemobiletest.config.TestContainersConfiguration;
import com.example.effectivemobiletest.data.TestDataFactory;
import com.example.effectivemobiletest.entity.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

@DataJpaTest
@Import(TestContainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/test-data.sql")
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void findAll_whenFindIsOk_thenReturnPageTasks() {
        Page<Task> pageTasks = taskRepository.findAll(TestDataFactory.PAGEABLE);

        Page<Task> testPageTasks = TestDataFactory.createPageTasks();

        Assertions.assertFalse(pageTasks.isEmpty());
        Assertions.assertEquals(pageTasks.getContent().get(0), testPageTasks.getContent().get(0));
        Assertions.assertEquals(pageTasks.getContent().get(1), testPageTasks.getContent().get(1));
    }

    @Test
    public void findAllByAuthorId_whenFindIsOk_thenReturnPageTasks() {
        Page<Task> pageTasks = taskRepository.findAllByAuthorId(TestDataFactory.USER_ID, TestDataFactory.PAGEABLE);

        Page<Task> testPageTasks = TestDataFactory.createPageTasks();

        Assertions.assertFalse(pageTasks.isEmpty());
        Assertions.assertEquals(pageTasks.getContent().get(0), testPageTasks.getContent().get(0));
    }

    @Test
    public void findAllByAuthorId_whenNotFound_thenReturnEmptyPageTasks() {
        Page<Task> pageTasks = taskRepository.findAllByAuthorId(UUID.randomUUID(), TestDataFactory.PAGEABLE);

        Assertions.assertTrue(pageTasks.isEmpty());
    }

    @Test
    public void findAllByExecutorId_whenFindIsOk_thenReturnPageTasks() {
        Page<Task> pageTasks = taskRepository.findAllByExecutorId(TestDataFactory.USER_ID, TestDataFactory.PAGEABLE);

        Page<Task> testPageTasks = TestDataFactory.createPageTasks();

        Assertions.assertFalse(pageTasks.isEmpty());
        Assertions.assertEquals(pageTasks.getContent().get(0), testPageTasks.getContent().get(0));
    }

    @Test
    public void findAllByExecutorId_whenNotFound_thenReturnEmptyPageTasks() {
        Page<Task> pageTasks = taskRepository.findAllByExecutorId(UUID.randomUUID(), TestDataFactory.PAGEABLE);

        Assertions.assertTrue(pageTasks.isEmpty());
    }
}