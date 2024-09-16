package com.example.effectivemobiletest.controller;

import com.example.effectivemobiletest.controller.handler.ExceptionHandlerController;
import com.example.effectivemobiletest.data.TestDataFactory;
import com.example.effectivemobiletest.data.TestDtoFactory;
import com.example.effectivemobiletest.dto.request.AddCommentOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.CreateTaskRequestDto;
import com.example.effectivemobiletest.dto.request.DeleteCommentFromTaskRequestDto;
import com.example.effectivemobiletest.dto.request.DeleteTaskRequestDto;
import com.example.effectivemobiletest.dto.request.GetAllCommentsOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.GetAllTasksByUserRequestDto;
import com.example.effectivemobiletest.dto.request.UpdateCommentOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.UpdateTaskRequestDto;
import com.example.effectivemobiletest.dto.response.CommentResponseDto;
import com.example.effectivemobiletest.dto.response.InfoMessageResponseDto;
import com.example.effectivemobiletest.dto.response.TaskResponseDto;
import com.example.effectivemobiletest.entity.Task;
import com.example.effectivemobiletest.entity.User;
import com.example.effectivemobiletest.exception.CommentNotFoundException;
import com.example.effectivemobiletest.exception.DeleteCommentAnotherUserException;
import com.example.effectivemobiletest.exception.DeleteTaskAnotherUserException;
import com.example.effectivemobiletest.exception.TaskNotFoundException;
import com.example.effectivemobiletest.exception.UpdateCommentAnotherUserException;
import com.example.effectivemobiletest.exception.UpdateTaskAnotherUserException;
import com.example.effectivemobiletest.exception.UserNotFoundException;
import com.example.effectivemobiletest.service.CommentService;
import com.example.effectivemobiletest.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class TaskManagerControllerTest {
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @InjectMocks
    private TaskManagerController taskManagerController;

    @Mock
    private TaskService taskService;

    @Mock
    private CommentService commentService;

    int page;
    int pageSize;
    Pageable pageable;

    Page<Task> taskPage;
    List<Task> taskList;
    User user;
    Task task;

    GetAllTasksByUserRequestDto getAllTasksByUserRequestDto;
    CreateTaskRequestDto createTaskRequestDto;
    UpdateTaskRequestDto updateTaskRequestDto;
    DeleteTaskRequestDto deleteTaskRequestDto;
    GetAllCommentsOnTaskRequestDto getAllCommentsOnTaskRequestDto;
    AddCommentOnTaskRequestDto addCommentOnTaskRequestDto;
    UpdateCommentOnTaskRequestDto updateCommentOnTaskRequestDto;
    DeleteCommentFromTaskRequestDto deleteCommentFromTaskRequestDto;

    TaskResponseDto createTaskResponseDto;
    TaskResponseDto updateTaskResponseDto;
    InfoMessageResponseDto deleteTaskInfoMessageResponseDto;
    List<TaskResponseDto> taskResponseDtoList;
    CommentResponseDto addCommentResponseDto;
    CommentResponseDto updateCommentResponseDto;
    InfoMessageResponseDto deleteCommentInfoMessageResponseDto;
    List<CommentResponseDto> commentResponseDtoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(taskManagerController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        page = TestDataFactory.PAGE;
        pageSize = TestDataFactory.PAGE_SIZE;
        pageable = TestDataFactory.PAGEABLE;

        taskPage = TestDataFactory.createPageTasks();
        taskList = TestDataFactory.createListTasks();
        user = TestDataFactory.createUser2();
        task = TestDataFactory.createTask();

        getAllTasksByUserRequestDto = TestDtoFactory.createGetAllTasksByUserRequestDto();
        createTaskRequestDto = TestDtoFactory.createCreateTaskRequestDto();
        updateTaskRequestDto = TestDtoFactory.createUpdateTaskRequestDto();
        deleteTaskRequestDto = TestDtoFactory.createDeleteTaskRequestDto();
        getAllCommentsOnTaskRequestDto = TestDtoFactory.createGetAllCommentsOnTaskRequestDto();
        addCommentOnTaskRequestDto = TestDtoFactory.createAddCommentOnTaskRequestDto();
        updateCommentOnTaskRequestDto = TestDtoFactory.createUpdateCommentOnTaskRequestDto();
        deleteCommentFromTaskRequestDto = TestDtoFactory.createDeleteCommentFromTaskRequestDto();

        createTaskResponseDto = TestDtoFactory.createTaskResponseDtoForCreateTask();
        updateTaskResponseDto = TestDtoFactory.createTaskResponseDtoForUpdateTask();
        deleteTaskInfoMessageResponseDto = TestDtoFactory.createInfoMessageResponseDtoForDeleteTask();
        taskResponseDtoList = TestDtoFactory.createListTaskResponseDto();
        addCommentResponseDto = TestDtoFactory.createAddCommentResponseDto();
        updateCommentResponseDto = TestDtoFactory.createUpdateCommentResponseDto();
        deleteCommentInfoMessageResponseDto = TestDtoFactory.createInfoMessageResponseDtoForDeleteComment();
        commentResponseDtoList = TestDtoFactory.createListCommentResponseDto();
    }

    @Test
    @SneakyThrows
    void getAllTasks_whenFindIsOk_thenReturnListTaskResponseDto() {
        String jsonResponseQuery = objectMapper.writeValueAsString(taskResponseDtoList);

        when(taskService.getAllTasks(page, pageSize)).thenReturn(taskResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseQuery))
                .andDo(print());

        verify(taskService, times(1)).getAllTasks(anyInt(), anyInt());
    }

    @Test
    @SneakyThrows
    void getAllTasksByAuthor_whenFindIsOk_thenReturnListTaskResponseDto() {
        String jsonRequestQuery = objectMapper.writeValueAsString(getAllTasksByUserRequestDto);
        String jsonResponseQuery = objectMapper.writeValueAsString(taskResponseDtoList);

        when(taskService.getAllTasksByAuthor(getAllTasksByUserRequestDto, page, pageSize)).thenReturn(taskResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/author")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseQuery))
                .andDo(print());

        verify(taskService, times(1))
                .getAllTasksByAuthor(any(GetAllTasksByUserRequestDto.class),
                        anyInt(), anyInt());
    }

    @Test
    @SneakyThrows
    void getAllTasksByAuthor_whenTaskNotFound_thenReturnErrorResponseDtoWithTaskNotFoundException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(getAllTasksByUserRequestDto);

        when(taskService.getAllTasksByAuthor(getAllTasksByUserRequestDto, page, pageSize))
                .thenThrow(TaskNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/author")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(taskService, times(1))
                .getAllTasksByAuthor(any(GetAllTasksByUserRequestDto.class),
                        anyInt(), anyInt());
    }

    @Test
    @SneakyThrows
    void getAllTasksByExecutor_whenFindIsOk_thenReturnListTaskResponseDto() {
        String jsonRequestQuery = objectMapper.writeValueAsString(getAllTasksByUserRequestDto);
        String jsonResponseQuery = objectMapper.writeValueAsString(taskResponseDtoList);

        when(taskService.getAllTasksByExecutor(getAllTasksByUserRequestDto, page, pageSize)).thenReturn(taskResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/executor")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseQuery))
                .andDo(print());

        verify(taskService, times(1))
                .getAllTasksByExecutor(any(GetAllTasksByUserRequestDto.class), anyInt(), anyInt());
    }

    @Test
    @SneakyThrows
    void getAllTasksByExecutor_whenTaskNotFound_thenReturnErrorResponseDtoWithTaskNotFoundException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(getAllTasksByUserRequestDto);

        when(taskService.getAllTasksByExecutor(getAllTasksByUserRequestDto, page, pageSize))
                .thenThrow(TaskNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/executor")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(taskService, times(1))
                .getAllTasksByExecutor(any(GetAllTasksByUserRequestDto.class), anyInt(), anyInt());
    }

    @Test
    @SneakyThrows
    void createTask_whenCreateIsOk_thenReturnTaskResponseDto() {
        String jsonRequestQuery = objectMapper.writeValueAsString(createTaskRequestDto);
        String jsonResponseQuery = objectMapper.writeValueAsString(createTaskResponseDto);

        when(taskService.createTask(createTaskRequestDto)).thenReturn(createTaskResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseQuery))
                .andDo(print());

        verify(taskService, times(1)).createTask(any(CreateTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void createTask_whenExecutorNotFound_thenReturnErrorResponseDtoWithUserNotFoundException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(createTaskRequestDto);

        when(taskService.createTask(createTaskRequestDto)).thenThrow(UserNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(taskService, times(1)).createTask(any(CreateTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void updateTask_whenUpdateIsOk_thenReturnTaskResponseDto() {
        String jsonRequestQuery = objectMapper.writeValueAsString(updateTaskRequestDto);
        String jsonResponseQuery = objectMapper.writeValueAsString(updateTaskResponseDto);

        when(taskService.updateTask(updateTaskRequestDto)).thenReturn(updateTaskResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseQuery))
                .andDo(print());

        verify(taskService, times(1)).updateTask(any(UpdateTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void updateTask_whenTaskNotFound_thenReturnErrorResponseDtoWithTaskNotFoundException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(updateTaskRequestDto);

        when(taskService.updateTask(updateTaskRequestDto)).thenThrow(TaskNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(taskService, times(1)).updateTask(any(UpdateTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void updateTask_whenUpdateTaskAnotherUser_thenReturnErrorResponseDtoWithUpdateTaskAnotherUserException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(updateTaskRequestDto);

        when(taskService.updateTask(updateTaskRequestDto)).thenThrow(UpdateTaskAnotherUserException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotAcceptable())
                .andDo(print());

        verify(taskService, times(1)).updateTask(any(UpdateTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void deleteTask_whenDeleteIsOk_thenReturnInfoMessageResponseDto() {
        String jsonRequestQuery = objectMapper.writeValueAsString(deleteTaskRequestDto);
        String jsonResponseQuery = objectMapper.writeValueAsString(deleteTaskInfoMessageResponseDto);

        when(taskService.deleteTask(deleteTaskRequestDto)).thenReturn(deleteTaskInfoMessageResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseQuery))
                .andDo(print());

        verify(taskService, times(1)).deleteTask(any(DeleteTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void deleteTask_whenTaskNotFound_thenReturnErrorResponseDtoWithTaskNotFoundException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(deleteTaskRequestDto);

        when(taskService.deleteTask(deleteTaskRequestDto)).thenThrow(TaskNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(taskService, times(1)).deleteTask(any(DeleteTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void deleteTask_whenDeleteTaskAnotherUser_thenReturnErrorResponseDtoWithDeleteTaskAnotherUserException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(deleteTaskRequestDto);

        when(taskService.deleteTask(deleteTaskRequestDto)).thenThrow(DeleteTaskAnotherUserException.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotAcceptable())
                .andDo(print());

        verify(taskService, times(1)).deleteTask(any(DeleteTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void getAllCommentsInTask_whenDeleteIsOk_thenReturnListCommentResponseDto() {
        String jsonRequestQuery = objectMapper.writeValueAsString(getAllCommentsOnTaskRequestDto);
        String jsonResponseQuery = objectMapper.writeValueAsString(commentResponseDtoList);

        when(commentService.getAllCommentsInTask(getAllCommentsOnTaskRequestDto, page, pageSize))
                .thenReturn(commentResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/comment")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseQuery))
                .andDo(print());

        verify(commentService, times(1))
                .getAllCommentsInTask(any(GetAllCommentsOnTaskRequestDto.class), anyInt(), anyInt());
    }

    @Test
    @SneakyThrows
    void getAllCommentsInTask_whenTaskNotFound_thenReturnErrorResponseWithTaskNotFoundException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(getAllCommentsOnTaskRequestDto);

        when(commentService.getAllCommentsInTask(getAllCommentsOnTaskRequestDto, page, pageSize))
                .thenThrow(TaskNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/comment")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(commentService, times(1))
                .getAllCommentsInTask(any(GetAllCommentsOnTaskRequestDto.class), anyInt(), anyInt());
    }

    @Test
    @SneakyThrows
    void addCommentOnTask_whenAddIsOk_thenReturnCommentResponseDto() {
        String jsonRequestQuery = objectMapper.writeValueAsString(addCommentOnTaskRequestDto);
        String jsonResponseQuery = objectMapper.writeValueAsString(addCommentResponseDto);

        when(commentService.addCommentOnTask(addCommentOnTaskRequestDto)).thenReturn(addCommentResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseQuery))
                .andDo(print());

        verify(commentService, times(1))
                .addCommentOnTask(any(AddCommentOnTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void addCommentOnTask_whenTaskNotFound_thenReturnErrorResponseWithTaskNotFoundException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(addCommentOnTaskRequestDto);

        when(commentService.addCommentOnTask(addCommentOnTaskRequestDto)).thenThrow(TaskNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(commentService, times(1))
                .addCommentOnTask(any(AddCommentOnTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void updateCommentOnTask_whenUpdateIsOk_thenReturnCommentResponseDto() {
        String jsonRequestQuery = objectMapper.writeValueAsString(updateCommentOnTaskRequestDto);
        String jsonResponseQuery = objectMapper.writeValueAsString(updateCommentResponseDto);

        when(commentService.updateCommentOnTask(updateCommentOnTaskRequestDto)).thenReturn(updateCommentResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseQuery))
                .andDo(print());

        verify(commentService, times(1))
                .updateCommentOnTask(any(UpdateCommentOnTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void updateCommentOnTask_whenCommentOrTaskNotFound_thenReturnErrorResponseWithAllNotFoundException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(updateCommentOnTaskRequestDto);

        when(commentService.updateCommentOnTask(updateCommentOnTaskRequestDto))
                .thenThrow(CommentNotFoundException.class, TaskNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(commentService, times(1))
                .updateCommentOnTask(any(UpdateCommentOnTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void updateCommentOnTask_whenUpdateCommentAnotherUser_thenReturnErrorResponseWithUpdateCommentAnotherUserException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(updateCommentOnTaskRequestDto);

        when(commentService.updateCommentOnTask(updateCommentOnTaskRequestDto))
                .thenThrow(UpdateCommentAnotherUserException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotAcceptable())
                .andDo(print());

        verify(commentService, times(1))
                .updateCommentOnTask(any(UpdateCommentOnTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void deleteCommentOnTask_whenUpdateIsOk_thenReturnCommentResponseDto() {
        String jsonRequestQuery = objectMapper.writeValueAsString(deleteCommentFromTaskRequestDto);
        String jsonResponseQuery = objectMapper.writeValueAsString(deleteCommentInfoMessageResponseDto);

        when(commentService.deleteCommentFromTask(deleteCommentFromTaskRequestDto))
                .thenReturn(deleteCommentInfoMessageResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseQuery))
                .andDo(print());

        verify(commentService, times(1))
                .deleteCommentFromTask(any(DeleteCommentFromTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void deleteCommentOnTask_whenCommentOrTaskNotFound_thenReturnErrorResponseDtoWithAllNotFoundException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(deleteCommentFromTaskRequestDto);

        when(commentService.deleteCommentFromTask(deleteCommentFromTaskRequestDto))
                .thenThrow(CommentNotFoundException.class, TaskNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(commentService, times(1))
                .deleteCommentFromTask(any(DeleteCommentFromTaskRequestDto.class));
    }

    @Test
    @SneakyThrows
    void deleteCommentOnTask_whenDeleteCommentAnotherUser_thenReturnErrorResponseDtoWithDeleteCommentAnotherUserException() {
        String jsonRequestQuery = objectMapper.writeValueAsString(deleteCommentFromTaskRequestDto);

        when(commentService.deleteCommentFromTask(deleteCommentFromTaskRequestDto))
                .thenThrow(DeleteCommentAnotherUserException.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestQuery))
                .andExpect(status().isNotAcceptable())
                .andDo(print());

        verify(commentService, times(1))
                .deleteCommentFromTask(any(DeleteCommentFromTaskRequestDto.class));
    }
}