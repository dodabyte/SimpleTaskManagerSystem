package com.example.effectivemobiletest.service.impl;

import com.example.effectivemobiletest.data.TestDataFactory;
import com.example.effectivemobiletest.data.TestDtoFactory;
import com.example.effectivemobiletest.dto.request.CreateTaskRequestDto;
import com.example.effectivemobiletest.dto.request.DeleteTaskRequestDto;
import com.example.effectivemobiletest.dto.request.GetAllTasksByUserRequestDto;
import com.example.effectivemobiletest.dto.request.UpdateTaskRequestDto;
import com.example.effectivemobiletest.dto.response.InfoMessageResponseDto;
import com.example.effectivemobiletest.dto.response.TaskResponseDto;
import com.example.effectivemobiletest.entity.Task;
import com.example.effectivemobiletest.entity.User;
import com.example.effectivemobiletest.exception.DeleteTaskAnotherUserException;
import com.example.effectivemobiletest.exception.TaskNotFoundException;
import com.example.effectivemobiletest.exception.UpdateTaskAnotherUserException;
import com.example.effectivemobiletest.exception.UserNotFoundException;
import com.example.effectivemobiletest.repository.TaskRepository;
import com.example.effectivemobiletest.repository.UserRepository;
import com.example.effectivemobiletest.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

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

    TaskResponseDto createTaskResponseDto;
    TaskResponseDto updateTaskResponseDto;
    InfoMessageResponseDto deleteTaskInfoMessageResponseDto;
    List<TaskResponseDto> taskResponseDtoList;

    @BeforeEach
    void setUp() {
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

        createTaskResponseDto = TestDtoFactory.createTaskResponseDtoForCreateTask();
        updateTaskResponseDto = TestDtoFactory.createTaskResponseDtoForUpdateTask();
        deleteTaskInfoMessageResponseDto = TestDtoFactory.createInfoMessageResponseDtoForDeleteTask();
        taskResponseDtoList = TestDtoFactory.createListTaskResponseDto();
    }

    @Test
    public void getAllTasks_whenGetIsOk_thenReturnTaskResponseDtoList() {
        Mockito.when(taskRepository.findAll(pageable)).thenReturn(taskPage);

        List<TaskResponseDto> result = taskService.getAllTasks(page, pageSize);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(taskResponseDtoList, result);
    }

    @Test
    public void getAllTasksByAuthor_whenGetIsOk_thenReturnTaskResponseDtoList() {
        Mockito.when(taskRepository.findAllByAuthorId(TestDataFactory.USER_ID, pageable)).thenReturn(taskPage);

        List<TaskResponseDto> result = taskService.getAllTasksByAuthor(getAllTasksByUserRequestDto, page, pageSize);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(taskResponseDtoList, result);
    }

    @Test
    public void getAllTasksByExecutor_whenGetIsOk_thenReturnTaskResponseDtoList() {
        Mockito.when(taskRepository.findAllByExecutorId(TestDataFactory.USER_ID, pageable)).thenReturn(taskPage);

        List<TaskResponseDto> result = taskService.getAllTasksByExecutor(getAllTasksByUserRequestDto, page, pageSize);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(taskResponseDtoList, result);
    }

    @Test
    public void createTask_whenCreateIsOk_thenReturnTaskResponseDto() {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(userRepository.findById(TestDataFactory.USER_ID)).thenReturn(Optional.of(user));
        Mockito.when(taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(task);

        TaskResponseDto result = taskService.createTask(createTaskRequestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(createTaskResponseDto, result);

        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.times(1)).save(ArgumentMatchers.any(Task.class));
    }

    @Test
    public void createTask_whenUserNotFound_thenThrowUserNotFoundException() {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(userRepository.findById(TestDataFactory.USER_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class,
                () -> taskService.createTask(createTaskRequestDto));

        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.never()).save(ArgumentMatchers.any(Task.class));
    }

    @Test
    public void updateTask_whenUpdateIsOk_thenReturnTaskResponseDto() {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(userRepository.findById(TestDataFactory.USER_ID)).thenReturn(Optional.of(user));
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(task);

        TaskResponseDto result = taskService.updateTask(updateTaskRequestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updateTaskResponseDto, result);

        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.times(1)).save(ArgumentMatchers.any(Task.class));
    }

    @Test
    public void updateTask_whenTaskNotFound_thenThrowTaskNotFoundException() {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(TaskNotFoundException.class,
                () -> taskService.updateTask(updateTaskRequestDto));

        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.never()).save(ArgumentMatchers.any(Task.class));
    }

    @Test
    public void updateTask_whenClientEditingSomeoneElseTask_thenThrowUpdateTaskAnotherUserException() {
        task.setAuthor(new User());

        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.of(task));

        Assertions.assertThrows(UpdateTaskAnotherUserException.class,
                () -> taskService.updateTask(updateTaskRequestDto));

        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.never()).save(ArgumentMatchers.any(Task.class));
    }

    @Test
    public void deleteTask_whenDeleteIsOk_thenReturnInfoMessageResponseDto() {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.of(task));

        InfoMessageResponseDto result = taskService.deleteTask(deleteTaskRequestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(deleteTaskInfoMessageResponseDto, result);

        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.times(1)).delete(ArgumentMatchers.any(Task.class));
    }

    @Test
    public void deleteTask_whenTaskNotFound_thenThrowTaskNotFoundException() {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(TaskNotFoundException.class,
                () -> taskService.deleteTask(deleteTaskRequestDto));

        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.never()).delete(ArgumentMatchers.any(Task.class));
    }

    @Test
    public void deleteTask_whenClientEditingSomeoneElseTask_thenThrowDeleteTaskAnotherUserException() {
        task.setAuthor(new User());

        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.of(task));

        Assertions.assertThrows(DeleteTaskAnotherUserException.class,
                () -> taskService.deleteTask(deleteTaskRequestDto));

        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.never()).delete(ArgumentMatchers.any(Task.class));
    }
}