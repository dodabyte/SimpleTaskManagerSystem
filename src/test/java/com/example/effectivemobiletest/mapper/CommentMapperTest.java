package com.example.effectivemobiletest.mapper;

import com.example.effectivemobiletest.data.TestDataFactory;
import com.example.effectivemobiletest.data.TestDtoFactory;
import com.example.effectivemobiletest.dto.response.CommentResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class CommentMapperTest {
    @Test
    public void fromCommentToCommentResponseDto_whenMapIsOk_thenReturnCommentResponseDto() {
        CommentResponseDto commentResponseDto =
                CommentMapper.INSTANCE.fromCommentToCommentResponseDto(TestDataFactory.createComment());

        CommentResponseDto testCommentResponseDto = TestDtoFactory.createAddCommentResponseDto();

        Assertions.assertEquals(commentResponseDto, testCommentResponseDto);
    }

    @Test
    public void fromCommentListToCommentResponseDtoList_whenMapIsOk_thenReturnListCommentResponseDto() {
        List<CommentResponseDto> commentResponseDtoList =
                CommentMapper.INSTANCE.fromCommentListToCommentResponseDtoList(TestDataFactory.createListComments());

        List<CommentResponseDto> testCommentResponseDtoList = TestDtoFactory.createListCommentResponseDto();

        Assertions.assertEquals(commentResponseDtoList, testCommentResponseDtoList);
    }
}