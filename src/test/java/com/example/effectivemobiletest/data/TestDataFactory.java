package com.example.effectivemobiletest.data;

import com.example.effectivemobiletest.entity.Comment;
import com.example.effectivemobiletest.entity.Task;
import com.example.effectivemobiletest.entity.User;
import com.example.effectivemobiletest.entity.enums.Priority;
import com.example.effectivemobiletest.entity.enums.Role;
import com.example.effectivemobiletest.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestDataFactory {
    public static final UUID TASK_ID = UUID.fromString("a474a9e2-ee33-4bc2-bdc5-fe88ecdfb3a3");
    public static final UUID COMMENT_ID = UUID.fromString("34b13ef6-3abf-4f20-bcfb-7d9562a2022d");
    public static final UUID USER_ID = UUID.fromString("2b5f2fee-e73f-467f-8399-235e9d4e473d");

    public static final int PAGE = 0;
    public static final int PAGE_SIZE = 3;
    public static final Pageable PAGEABLE = PageRequest.of(PAGE, PAGE_SIZE);

    public static User createUser1() {
        return User.builder()
                .id(UUID.fromString("e71a25f6-aea7-4b3e-a0c3-4628e454ad1f"))
                .username("test")
                .fullName("Тестов Тест Тестович")
                .email("test@example.com")
                .password("$2a$10$eqR4GFPrh82zsUTR50woFeN.PL59i6m7jMR6KwF.WBwyJR6ixLZjS")
                .registrationDate(LocalDateTime.parse("2024-09-13T14:09:21.531306"))
                .role(Role.ROLE_USER)
                .build();
    }

    public static User createUser2() {
        return User.builder()
                .id(USER_ID)
                .username("test2")
                .fullName("Тестов2 Тест2 Тестович2")
                .email("test2@example.com")
                .password("$2a$10$89jmiRa.2X5FZX26zv91j.VBLMLAvkwKwI9VoiA9seTik0ZNkGO3G")
                .registrationDate(LocalDateTime.parse("2024-08-29T18:52:26.918578"))
                .role(Role.ROLE_USER)
                .build();
    }

    public static Task createTask() {
        return Task.builder()
                .id(TASK_ID)
                .author(createUser2())
                .executor(createUser2())
                .title("Багфикс TaskService")
                .description(null)
                .status(Status.WAITING)
                .priority(Priority.MEDIUM)
                .comments(new ArrayList<>())
                .build();
    }

    public static Comment createComment() {
        return Comment.builder()
                .id(COMMENT_ID)
                .task(createTask())
                .author(createUser2())
                .text("Я помогу тебе с задачей ;)")
                .commentTime(LocalDateTime.parse("2024-08-30T19:25:02.994020"))
                .modificationTime(null)
                .build();
    }

    public static List<Comment> createListComments() {
        return List.of(
                    createComment(),
                    Comment.builder()
                            .id(UUID.fromString("24136f87-a9db-460c-b756-38688bb1c8a4"))
                            .task(createTask())
                            .author(createUser2())
                            .text("опана")
                            .commentTime(LocalDateTime.parse("2024-08-29T19:23:24.693127"))
                            .modificationTime(null)
                            .build()
                );
    }

    public static Page<Comment> createPageComments() {
        return new PageImpl<>(createListComments());
    }

    public static List<Task> createListTasks() {
        return List.of(
                createTask(),
                Task.builder()
                        .id(UUID.fromString("79d9c322-73dc-4e53-aad6-28c408188c5a"))
                        .author(createUser1())
                        .executor(createUser2())
                        .title("Настройка Kafka")
                        .description(null)
                        .status(Status.WAITING)
                        .priority(Priority.HIGH)
                        .comments(new ArrayList<>())
                        .build()
        );
    }

    public static Page<Task> createPageTasks() {
        return new PageImpl<>(createListTasks());
    }
}
