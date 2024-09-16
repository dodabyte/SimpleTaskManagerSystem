package com.example.effectivemobiletest.mapper;

import com.example.effectivemobiletest.data.TestDataFactory;
import com.example.effectivemobiletest.data.TestDtoFactory;
import com.example.effectivemobiletest.dto.response.TaskResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TaskMapperTest {
    @Test
    public void fromTaskToTaskResponseDto_whenMapIsOk_thenReturnTaskResponseDto() {
        TaskResponseDto taskResponseDto =
                TaskMapper.INSTANCE.fromTaskToTaskResponseDto(TestDataFactory.createTask());

        TaskResponseDto testTaskResponseDto = TestDtoFactory.createTaskResponseDtoForCreateTask();

        Assertions.assertEquals(taskResponseDto, testTaskResponseDto);
    }

    @Test
    public void fromTaskListToTaskResponseDtoList_whenMapIsOk_thenReturnListTaskResponseDto() {
        List<TaskResponseDto> taskResponseDtoList =
                TaskMapper.INSTANCE.fromTaskListToTaskResponseDtoList(TestDataFactory.createListTasks());

        List<TaskResponseDto> testTaskResponseDtoList = TestDtoFactory.createListTaskResponseDto();

        Assertions.assertEquals(taskResponseDtoList, testTaskResponseDtoList);
    }
}