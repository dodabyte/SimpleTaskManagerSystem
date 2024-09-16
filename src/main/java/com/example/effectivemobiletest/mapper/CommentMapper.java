package com.example.effectivemobiletest.mapper;

import com.example.effectivemobiletest.dto.response.CommentResponseDto;
import com.example.effectivemobiletest.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "commentId", source = "id")
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "text", source = "text")
    @Mapping(target = "commentTime", source = "commentTime")
    @Mapping(target = "modificationTime", source = "modificationTime")
    CommentResponseDto fromCommentToCommentResponseDto(Comment comment);

    List<CommentResponseDto> fromCommentListToCommentResponseDtoList(List<Comment> commentList);
}
