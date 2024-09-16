package com.example.effectivemobiletest.data;

import com.example.effectivemobiletest.dto.request.AddCommentOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.CreateTaskRequestDto;
import com.example.effectivemobiletest.dto.request.DeleteCommentFromTaskRequestDto;
import com.example.effectivemobiletest.dto.request.DeleteTaskRequestDto;
import com.example.effectivemobiletest.dto.request.GetAllCommentsOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.GetAllTasksByUserRequestDto;
import com.example.effectivemobiletest.dto.request.SignInRequestDto;
import com.example.effectivemobiletest.dto.request.SignUpRequestDto;
import com.example.effectivemobiletest.dto.request.UpdateCommentOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.UpdateTaskRequestDto;
import com.example.effectivemobiletest.dto.response.CommentResponseDto;
import com.example.effectivemobiletest.dto.response.InfoMessageResponseDto;
import com.example.effectivemobiletest.dto.response.JwtAuthenticationResponseDto;
import com.example.effectivemobiletest.dto.response.TaskResponseDto;
import com.example.effectivemobiletest.dto.response.UserResponseDto;
import com.example.effectivemobiletest.entity.Comment;
import com.example.effectivemobiletest.entity.Task;
import com.example.effectivemobiletest.entity.enums.Priority;
import com.example.effectivemobiletest.entity.enums.Role;
import com.example.effectivemobiletest.entity.enums.Status;
import com.example.effectivemobiletest.util.MessageUtil;

import java.time.LocalDateTime;
import java.util.List;

public class TestDtoFactory {
    public static GetAllCommentsOnTaskRequestDto createGetAllCommentsOnTaskRequestDto() {
        return GetAllCommentsOnTaskRequestDto.builder()
                .taskId(TestDataFactory.TASK_ID.toString())
                .build();
    }

    public static AddCommentOnTaskRequestDto createAddCommentOnTaskRequestDto() {
        return AddCommentOnTaskRequestDto.builder()
                .taskId(TestDataFactory.TASK_ID.toString())
                .comment("Я помогу тебе с задачей ;)")
                .build();
    }

    public static UpdateCommentOnTaskRequestDto createUpdateCommentOnTaskRequestDto() {
        return UpdateCommentOnTaskRequestDto.builder()
                .commentId(TestDataFactory.COMMENT_ID.toString())
                .comment("Возможно, я смогу помочь тебе с задачей, но не факт)))")
                .build();
    }

    public static DeleteCommentFromTaskRequestDto createDeleteCommentFromTaskRequestDto() {
        return DeleteCommentFromTaskRequestDto.builder()
                .commentId(TestDataFactory.COMMENT_ID.toString())
                .build();
    }

    public static CommentResponseDto createAddCommentResponseDto() {
        return CommentResponseDto.builder()
                .commentId(TestDataFactory.COMMENT_ID.toString())
                .taskId(TestDataFactory.TASK_ID.toString())
                .authorId(TestDataFactory.USER_ID.toString())
                .text("Я помогу тебе с задачей ;)")
                .commentTime(LocalDateTime.parse("2024-08-30T19:25:02.994020"))
                .modificationTime(null)
                .build();
    }

    public static CommentResponseDto createUpdateCommentResponseDto() {
        return CommentResponseDto.builder()
                .commentId(TestDataFactory.COMMENT_ID.toString())
                .taskId(TestDataFactory.TASK_ID.toString())
                .authorId(TestDataFactory.USER_ID.toString())
                .text("Возможно, я смогу помочь тебе с задачей, но не факт)))")
                .commentTime(LocalDateTime.parse("2024-08-30T19:25:02.994020"))
                .modificationTime(LocalDateTime.parse("2024-08-30T19:25:02.994020").plusDays(1))
                .build();
    }

    public static List<CommentResponseDto> createListCommentResponseDto() {
        return List.of(
                    createAddCommentResponseDto(),
                    CommentResponseDto.builder()
                            .commentId("24136f87-a9db-460c-b756-38688bb1c8a4")
                            .taskId(TestDataFactory.TASK_ID.toString())
                            .authorId(TestDataFactory.USER_ID.toString())
                            .text("опана")
                            .commentTime(LocalDateTime.parse("2024-08-29T19:23:24.693127"))
                            .modificationTime(null)
                            .build()
                );
    }

    public static InfoMessageResponseDto createInfoMessageResponseDtoForDeleteComment() {
        return InfoMessageResponseDto.builder()
                .id(TestDataFactory.COMMENT_ID.toString())
                .type(Comment.class.getTypeName())
                .message(MessageUtil.DELETE_SUCCESS_MESSAGE)
                .build();
    }

    public static InfoMessageResponseDto createInfoMessageResponseDtoForDeleteTask() {
        return InfoMessageResponseDto.builder()
                .id(TestDataFactory.TASK_ID.toString())
                .type(Task.class.getTypeName())
                .message(MessageUtil.DELETE_SUCCESS_MESSAGE)
                .build();
    }

    public static GetAllTasksByUserRequestDto createGetAllTasksByUserRequestDto() {
        return GetAllTasksByUserRequestDto.builder()
                .userId(TestDataFactory.USER_ID.toString())
                .build();
    }

    public static CreateTaskRequestDto createCreateTaskRequestDto() {
        return CreateTaskRequestDto.builder()
                .executorId(TestDataFactory.USER_ID.toString())
                .title("Багфикс TaskService")
                .description(null)
                .priority(Priority.MEDIUM)
                .build();
    }

    public static UpdateTaskRequestDto createUpdateTaskRequestDto() {
        return UpdateTaskRequestDto.builder()
                .taskId(TestDataFactory.TASK_ID.toString())
                .executorId(TestDataFactory.USER_ID.toString())
                .title("Багфикс TaskService и CommentService")
                .description(null)
                .status(Status.IN_PROGRESS)
                .priority(Priority.MEDIUM)
                .build();
    }

    public static DeleteTaskRequestDto createDeleteTaskRequestDto() {
        return DeleteTaskRequestDto.builder()
                .taskId(TestDataFactory.TASK_ID.toString())
                .build();
    }

    public static TaskResponseDto createTaskResponseDtoForCreateTask() {
        return TaskResponseDto.builder()
                .taskId(TestDataFactory.TASK_ID.toString())
                .authorId(TestDataFactory.USER_ID.toString())
                .executorId(TestDataFactory.USER_ID.toString())
                .title("Багфикс TaskService")
                .description(null)
                .status(Status.WAITING)
                .priority(Priority.MEDIUM)
                .build();
    }

    public static TaskResponseDto createTaskResponseDtoForUpdateTask() {
        return TaskResponseDto.builder()
                .taskId(TestDataFactory.TASK_ID.toString())
                .authorId(TestDataFactory.USER_ID.toString())
                .executorId(TestDataFactory.USER_ID.toString())
                .title("Багфикс TaskService и CommentService")
                .description(null)
                .status(Status.IN_PROGRESS)
                .priority(Priority.MEDIUM)
                .build();
    }

    public static List<TaskResponseDto> createListTaskResponseDto() {
        return List.of(
                createTaskResponseDtoForCreateTask(),
                TaskResponseDto.builder()
                        .taskId("79d9c322-73dc-4e53-aad6-28c408188c5a")
                        .authorId("e71a25f6-aea7-4b3e-a0c3-4628e454ad1f")
                        .executorId(TestDataFactory.USER_ID.toString())
                        .title("Настройка Kafka")
                        .description(null)
                        .status(Status.WAITING)
                        .priority(Priority.HIGH)
                        .build()
        );
    }

    public static SignUpRequestDto createSignUpRequestDto() {
        return SignUpRequestDto.builder()
                .username("test")
                .fullName("Тестов Тест Тестович")
                .email("test@example.com")
                .password("rand-pass-1234!")
                .build();
    }

    public static SignInRequestDto createSignInRequestDto() {
        return SignInRequestDto.builder()
                .email("test@example.com")
                .password("rand-pass-1234!")
                .build();
    }

    public static JwtAuthenticationResponseDto createJwtAuthenticationResponseDto() {
        return JwtAuthenticationResponseDto.builder()
                .token("eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOiJlNzFhMjVmNi1hZWE3LTRiM2UtYTBjMy00NjI4ZTQ1NGFkMWYiLCJlbWFpbCI6InRlc3RAZXhhbXBsZS5jb20iLCJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNzI2MjExMzYxLCJleHAiOjE3MjYzNTUzNjF9.vgs3OHJq-Lg2u-LNNddrNVfV2olXQa3s5v1fzACZbiA")
                .build();
    }

    public static UserResponseDto createUserResponseDto() {
        return UserResponseDto.builder()
                .userId(TestDataFactory.USER_ID.toString())
                .username("test2")
                .fullName("Тестов2 Тест2 Тестович2")
                .email("test2@example.com")
                .registrationDate(LocalDateTime.parse("2024-08-29T18:52:26.918578"))
                .role(Role.ROLE_USER)
                .build();
    }
}
