package com.example.effectivemobiletest.controller;

import com.example.effectivemobiletest.dto.request.AddCommentOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.CreateTaskRequestDto;
import com.example.effectivemobiletest.dto.request.DeleteCommentFromTaskRequestDto;
import com.example.effectivemobiletest.dto.request.DeleteTaskRequestDto;
import com.example.effectivemobiletest.dto.request.GetAllCommentsOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.GetAllTasksByUserRequestDto;
import com.example.effectivemobiletest.dto.request.UpdateCommentOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.UpdateTaskRequestDto;
import com.example.effectivemobiletest.dto.response.CommentResponseDto;
import com.example.effectivemobiletest.dto.response.ErrorResponseDto;
import com.example.effectivemobiletest.dto.response.InfoMessageResponseDto;
import com.example.effectivemobiletest.dto.response.TaskResponseDto;
import com.example.effectivemobiletest.service.CommentService;
import com.example.effectivemobiletest.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Система управления задачами", description = "Взаимодействие с задачми и комментариями в задачах")
public class TaskManagerController {
    private final TaskService taskService;
    private final CommentService commentService;

    @Operation(summary = "Получение всех задач")
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(taskService.getAllTasks(page, pageSize));
    }

    @Operation(summary = "Получение всех задач по указанному автору")
    @GetMapping("/author")
    public ResponseEntity<List<TaskResponseDto>> getAllTasksByAuthor(@RequestBody @Validated GetAllTasksByUserRequestDto dto,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(taskService.getAllTasksByAuthor(dto, page, pageSize));
    }

    @Operation(summary = "Получение всех задач по указанному исполнителю")
    @GetMapping("/executor")
    public ResponseEntity<List<TaskResponseDto>> getAllTasksByExecutor(@RequestBody @Validated GetAllTasksByUserRequestDto dto,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(taskService.getAllTasksByExecutor(dto, page, pageSize));
    }

    @Operation(summary = "Создание задачи",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponseDto.class)
                            )}
                    ),
                    @ApiResponse(responseCode = "404", description = "UserNotFoundException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 404,
                                              "error": "Исполнитель с указанным идентификатором не найден",
                                              "path": "/tasks"
                                            }
                                            """)
                                    }
                            )}
                    )
            }
    )
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Validated CreateTaskRequestDto dto) {
        return ResponseEntity.ok(taskService.createTask(dto));
    }

    @Operation(summary = "Редактирование задачи",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponseDto.class)
                            )}
                    ),
                    @ApiResponse(responseCode = "404", description = "TaskNotFoundException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 404,
                                              "error": "Указанная задача не найдена",
                                              "path": "/tasks"
                                            }
                                            """)
                                    }
                            )}
                    ),
                    @ApiResponse(responseCode = "406", description = "UpdateTaskAnotherUserException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 406,
                                              "error": "Выбранная задача не может редактироваться чужим пользователем",
                                              "path": "/tasks"
                                            }
                                            """)
                                    }
                            )}
                    )
            }
    )
    @PutMapping
    public ResponseEntity<TaskResponseDto> updateTask(@RequestBody @Validated UpdateTaskRequestDto dto) {
        return ResponseEntity.ok(taskService.updateTask(dto));
    }

    @Operation(summary = "Удаление задачи",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponseDto.class)
                            )}
                    ),
                    @ApiResponse(responseCode = "404", description = "TaskNotFoundException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 404,
                                              "error": "Указанная задача не найдена",
                                              "path": "/tasks"
                                            }
                                            """)
                                    }
                            )}
                    ),
                    @ApiResponse(responseCode = "406", description = "DeleteTaskAnotherUserException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 406,
                                              "error": "Выбранная задача не может удаляться чужим пользователем",
                                              "path": "/tasks"
                                            }
                                            """)
                                    }
                            )}
                    )
            }
    )
    @DeleteMapping
    public ResponseEntity<InfoMessageResponseDto> deleteTask(@RequestBody @Validated DeleteTaskRequestDto dto) {
        return ResponseEntity.ok(taskService.deleteTask(dto));
    }

    @Operation(summary = "Получение всех комментариев задачи")
    @GetMapping("/comment")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsInTask(@RequestBody @Validated GetAllCommentsOnTaskRequestDto dto,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(commentService.getAllCommentsInTask(dto, page, pageSize));
    }

    @Operation(summary = "Добавление комментария к задаче",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponseDto.class)
                            )}
                    ),
                    @ApiResponse(responseCode = "404", description = "TaskNotFoundException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 404,
                                              "error": "Указанная задача не найдена",
                                              "path": "/tasks/comment"
                                            }
                                            """)
                                    }
                            )}
                    )
            }
    )
    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> addCommentOnTask(@RequestBody @Validated AddCommentOnTaskRequestDto dto) {
        return ResponseEntity.ok(commentService.addCommentOnTask(dto));
    }

    @Operation(summary = "Редактирование комментария к задаче",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponseDto.class)
                            )}
                    ),
                    @ApiResponse(responseCode = "404", description = "CommentNotFoundException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 404,
                                              "error": "Указанный комментарий не найден",
                                              "path": "/tasks"
                                            }
                                            """)
                                    }
                            )}
                    ),
                    @ApiResponse(responseCode = "404", description = "TaskNotFoundException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 404,
                                              "error": "Указанная задача не найдена",
                                              "path": "/tasks"
                                            }
                                            """)
                                    }
                            )}
                    ),
                    @ApiResponse(responseCode = "406", description = "UpdateCommentAnotherUserException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 406,
                                              "error": "Нельзя редактировать комментарий другого пользователя",
                                              "path": "/tasks/comment"
                                            }
                                            """)
                                    }
                            )}
                    )
            }
    )
    @PutMapping("/comment")
    public ResponseEntity<CommentResponseDto> updateCommentOnTask(@RequestBody @Validated UpdateCommentOnTaskRequestDto dto) {
        return ResponseEntity.ok(commentService.updateCommentOnTask(dto));
    }

    @Operation(summary = "Удаление комментария из задачи",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponseDto.class)
                            )}
                    ),
                    @ApiResponse(responseCode = "404", description = "CommentNotFoundException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 404,
                                              "error": "Указанный комментарий не найден",
                                              "path": "/tasks"
                                            }
                                            """)
                                    }
                            )}
                    ),
                    @ApiResponse(responseCode = "404", description = "TaskNotFoundException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 404,
                                              "error": "Указанная задача не найдена",
                                              "path": "/tasks"
                                            }
                                            """)
                                    }
                            )}
                    ),
                    @ApiResponse(responseCode = "406", description = "DeleteCommentAnotherUserException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 406,
                                              "error": "Нельзя удалить комментарий другого пользователя",
                                              "path": "/tasks/comment"
                                            }
                                            """)
                                    }
                            )}
                    )
            }
    )
    @DeleteMapping("/comment")
    public ResponseEntity<InfoMessageResponseDto> deleteCommentOnTask(@RequestBody @Validated DeleteCommentFromTaskRequestDto dto) {
        return ResponseEntity.ok(commentService.deleteCommentFromTask(dto));
    }
}
