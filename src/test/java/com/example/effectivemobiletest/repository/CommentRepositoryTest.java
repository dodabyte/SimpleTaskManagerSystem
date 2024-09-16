package com.example.effectivemobiletest.repository;

import com.example.effectivemobiletest.config.TestContainersConfiguration;
import com.example.effectivemobiletest.data.TestDataFactory;
import com.example.effectivemobiletest.entity.Comment;
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
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void findAllByTaskId_whenFindIsOk_thenReturnPageComments() {
        Page<Comment> pageComments = commentRepository.findAllByTaskId(TestDataFactory.TASK_ID, TestDataFactory.PAGEABLE);

        Page<Comment> testPageComments = TestDataFactory.createPageComments();

        Assertions.assertFalse(pageComments.isEmpty());
        Assertions.assertEquals(pageComments.getContent().get(0), testPageComments.getContent().get(0));
        Assertions.assertEquals(pageComments.getContent().get(1), testPageComments.getContent().get(1));
    }

    @Test
    public void findAllByTaskId_whenNotFound_thenReturnEmptyPageComments() {
        Page<Comment> pageComments = commentRepository.findAllByTaskId(UUID.randomUUID(), TestDataFactory.PAGEABLE);

        Assertions.assertTrue(pageComments.isEmpty());
    }
}