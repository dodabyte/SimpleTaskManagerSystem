package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.request.CreateTaskRequestDto;
import com.example.effectivemobiletest.dto.request.DeleteTaskRequestDto;
import com.example.effectivemobiletest.dto.request.GetAllTasksByUserRequestDto;
import com.example.effectivemobiletest.dto.request.UpdateTaskRequestDto;
import com.example.effectivemobiletest.dto.response.InfoMessageResponseDto;
import com.example.effectivemobiletest.dto.response.TaskResponseDto;

import java.util.List;

public interface TaskService {
    List<TaskResponseDto> getAllTasks(int page, int pageSize);

    List<TaskResponseDto> getAllTasksByAuthor(GetAllTasksByUserRequestDto dto, int page, int pageSize);

    List<TaskResponseDto> getAllTasksByExecutor(GetAllTasksByUserRequestDto dto, int page, int pageSize);

    TaskResponseDto createTask(CreateTaskRequestDto dto);

    TaskResponseDto updateTask(UpdateTaskRequestDto dto);

    InfoMessageResponseDto deleteTask(DeleteTaskRequestDto dto);
}
