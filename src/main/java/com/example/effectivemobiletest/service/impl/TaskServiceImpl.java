package com.example.effectivemobiletest.service.impl;

import com.example.effectivemobiletest.dto.request.CreateTaskRequestDto;
import com.example.effectivemobiletest.dto.request.DeleteTaskRequestDto;
import com.example.effectivemobiletest.dto.request.GetAllTasksByUserRequestDto;
import com.example.effectivemobiletest.dto.request.UpdateTaskRequestDto;
import com.example.effectivemobiletest.dto.response.InfoMessageResponseDto;
import com.example.effectivemobiletest.dto.response.TaskResponseDto;
import com.example.effectivemobiletest.entity.Task;
import com.example.effectivemobiletest.entity.User;
import com.example.effectivemobiletest.entity.enums.Status;
import com.example.effectivemobiletest.exception.DeleteTaskAnotherUserException;
import com.example.effectivemobiletest.exception.TaskNotFoundException;
import com.example.effectivemobiletest.exception.UpdateTaskAnotherUserException;
import com.example.effectivemobiletest.exception.UserNotFoundException;
import com.example.effectivemobiletest.mapper.TaskMapper;
import com.example.effectivemobiletest.repository.TaskRepository;
import com.example.effectivemobiletest.repository.UserRepository;
import com.example.effectivemobiletest.service.TaskService;
import com.example.effectivemobiletest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.effectivemobiletest.util.MessageUtil.DELETE_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    @Override
    public List<TaskResponseDto> getAllTasks(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Task> tasks = taskRepository.findAll(pageable).getContent();
        return TaskMapper.INSTANCE.fromTaskListToTaskResponseDtoList(tasks);
    }

    @Override
    public List<TaskResponseDto> getAllTasksByAuthor(GetAllTasksByUserRequestDto dto, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Task> tasks = taskRepository.findAllByAuthorId(UUID.fromString(dto.getUserId()), pageable).getContent();
        return TaskMapper.INSTANCE.fromTaskListToTaskResponseDtoList(tasks);
    }

    @Override
    public List<TaskResponseDto> getAllTasksByExecutor(GetAllTasksByUserRequestDto dto, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Task> tasks = taskRepository.findAllByExecutorId(UUID.fromString(dto.getUserId()), pageable).getContent();
        return TaskMapper.INSTANCE.fromTaskListToTaskResponseDtoList(tasks);
    }

    @Override
    public TaskResponseDto createTask(CreateTaskRequestDto dto) {
        User author = userService.getCurrentUser();

        User executor = getUser(dto.getExecutorId(), "Исполнитель с указанным идентификатором не найден");

        Task task = Task.builder()
                .author(author)
                .executor(executor)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(Status.WAITING)
                .priority(dto.getPriority())
                .comments(new ArrayList<>())
                .build();

        Task savedTask = taskRepository.save(task);

        return TaskMapper.INSTANCE.fromTaskToTaskResponseDto(savedTask);
    }

    @Override
    public TaskResponseDto updateTask(UpdateTaskRequestDto dto) {
        User author = userService.getCurrentUser();
        Task task = getTask(dto.getTaskId());

        if (!author.equals(task.getAuthor())) {
            throw new UpdateTaskAnotherUserException();
        }

        task.setAuthor(author);
        task.setExecutor(dto.getExecutorId() != null ? getUser(dto.getExecutorId(),
                "Исполнитель с указанным идентификатором не найден") : task.getExecutor());
        task.setTitle(dto.getTitle() != null ? dto.getTitle() : task.getTitle());
        task.setDescription(dto.getDescription() != null ? dto.getDescription() : task.getDescription());
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : task.getStatus());
        task.setPriority(dto.getPriority() != null ? dto.getPriority() : task.getPriority());

        Task savedTask = taskRepository.save(task);

        return TaskMapper.INSTANCE.fromTaskToTaskResponseDto(savedTask);
    }

    @Override
    public InfoMessageResponseDto deleteTask(DeleteTaskRequestDto dto) {
        User user = userService.getCurrentUser();
        Task task = getTask(dto.getTaskId());

        if (!user.getId().equals(task.getAuthor().getId())) {
            throw new DeleteTaskAnotherUserException();
        }

        taskRepository.delete(task);

        return InfoMessageResponseDto.builder()
                .id(task.getId().toString())
                .type(task.getClass().getTypeName())
                .message(DELETE_SUCCESS_MESSAGE)
                .build();
    }

    private User getUser(String userId, String errorMessage) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFoundException(errorMessage));
    }

    private Task getTask(String taskId) {
        return taskRepository.findById(UUID.fromString(taskId))
                .orElseThrow(TaskNotFoundException::new);
    }
}
