package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.request.AddCommentOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.DeleteCommentFromTaskRequestDto;
import com.example.effectivemobiletest.dto.request.GetAllCommentsOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.UpdateCommentOnTaskRequestDto;
import com.example.effectivemobiletest.dto.response.CommentResponseDto;
import com.example.effectivemobiletest.dto.response.InfoMessageResponseDto;

import java.util.List;

public interface CommentService {
    List<CommentResponseDto> getAllCommentsInTask(GetAllCommentsOnTaskRequestDto dto, int page, int pageSize);

    CommentResponseDto addCommentOnTask(AddCommentOnTaskRequestDto dto);

    CommentResponseDto updateCommentOnTask(UpdateCommentOnTaskRequestDto dto);

    InfoMessageResponseDto deleteCommentFromTask(DeleteCommentFromTaskRequestDto dto);
}
