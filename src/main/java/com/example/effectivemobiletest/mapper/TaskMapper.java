package com.example.effectivemobiletest.mapper;

import com.example.effectivemobiletest.dto.response.TaskResponseDto;
import com.example.effectivemobiletest.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "taskId", source = "id")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "executorId", source = "executor.id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "priority", source = "priority")
    TaskResponseDto fromTaskToTaskResponseDto(Task task);

    List<TaskResponseDto> fromTaskListToTaskResponseDtoList(List<Task> taskList);
}
