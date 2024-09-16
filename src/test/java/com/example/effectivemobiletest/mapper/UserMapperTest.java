package com.example.effectivemobiletest.mapper;

import com.example.effectivemobiletest.data.TestDataFactory;
import com.example.effectivemobiletest.data.TestDtoFactory;
import com.example.effectivemobiletest.dto.response.UserResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserMapperTest {
    @Test
    public void fromTaskToTaskResponseDto_whenMapIsOk_thenReturnTaskResponseDto() {
        UserResponseDto userResponseDto =
                UserMapper.INSTANCE.fromUserToUserResponseDto(TestDataFactory.createUser2());

        UserResponseDto testUserResponseDto = TestDtoFactory.createUserResponseDto();

        Assertions.assertEquals(userResponseDto, testUserResponseDto);
    }
}