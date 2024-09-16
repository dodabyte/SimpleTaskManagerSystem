package com.example.effectivemobiletest.service.impl;

import com.example.effectivemobiletest.dto.request.AddCommentOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.DeleteCommentFromTaskRequestDto;
import com.example.effectivemobiletest.dto.request.GetAllCommentsOnTaskRequestDto;
import com.example.effectivemobiletest.dto.request.UpdateCommentOnTaskRequestDto;
import com.example.effectivemobiletest.dto.response.CommentResponseDto;
import com.example.effectivemobiletest.dto.response.InfoMessageResponseDto;
import com.example.effectivemobiletest.entity.Comment;
import com.example.effectivemobiletest.entity.Task;
import com.example.effectivemobiletest.entity.User;
import com.example.effectivemobiletest.exception.CommentNotFoundException;
import com.example.effectivemobiletest.exception.DeleteCommentAnotherUserException;
import com.example.effectivemobiletest.exception.TaskNotFoundException;
import com.example.effectivemobiletest.exception.UpdateCommentAnotherUserException;
import com.example.effectivemobiletest.mapper.CommentMapper;
import com.example.effectivemobiletest.repository.CommentRepository;
import com.example.effectivemobiletest.repository.TaskRepository;
import com.example.effectivemobiletest.service.CommentService;
import com.example.effectivemobiletest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.effectivemobiletest.util.MessageUtil.DELETE_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    private final UserService userService;

    @Override
    @Transactional
    public List<CommentResponseDto> getAllCommentsInTask(GetAllCommentsOnTaskRequestDto dto, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Comment> comments = commentRepository.findAllByTaskId(UUID.fromString(dto.getTaskId()), pageable).getContent();
        return CommentMapper.INSTANCE.fromCommentListToCommentResponseDtoList(comments);
    }

    @Override
    public CommentResponseDto addCommentOnTask(AddCommentOnTaskRequestDto dto) {
        User user = userService.getCurrentUser();

        Task task = checkAndGetTask(dto.getTaskId());

        Comment savedComment = addCommentAndUpdateTask(user, task, dto.getComment());

        return CommentMapper.INSTANCE.fromCommentToCommentResponseDto(savedComment);
    }

    @Override
    public CommentResponseDto updateCommentOnTask(UpdateCommentOnTaskRequestDto dto) {
        Comment comment = checkAndGetComment(dto.getCommentId());

        Task task = checkAndGetTask(comment.getTask().getId().toString());

        if (!checkAuthorTask(task)) {
            throw new UpdateCommentAnotherUserException();
        }

        Comment updatedComment = updateAndSaveComment(comment, dto.getComment());

        return CommentMapper.INSTANCE.fromCommentToCommentResponseDto(updatedComment);
    }

    @Override
    public InfoMessageResponseDto deleteCommentFromTask(DeleteCommentFromTaskRequestDto dto) {
        Comment comment = checkAndGetComment(dto.getCommentId());

        Task task = checkAndGetTask(comment.getTask().getId().toString());

        if (!checkAuthorTask(task)) {
            throw new DeleteCommentAnotherUserException();
        }

        commentRepository.delete(comment);

        return InfoMessageResponseDto.builder()
                .id(comment.getId().toString())
                .type(comment.getClass().getTypeName())
                .message(DELETE_SUCCESS_MESSAGE)
                .build();
    }

    private Comment addCommentAndUpdateTask(User user, Task task, String commentText) {
        Comment comment = Comment.builder()
                .author(user)
                .text(commentText)
                .commentTime(LocalDateTime.now())
                .task(task)
                .build();

        Comment savedComment = commentRepository.save(comment);

        task.getComments().add(savedComment);

        return savedComment;
    }

    private Comment updateAndSaveComment(Comment comment, String commentText) {
        comment.setText(commentText != null && !commentText.isEmpty() ? commentText : comment.getText());
        comment.setModificationTime(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    private boolean checkAuthorTask(Task task) {
        User user = userService.getCurrentUser();
        return task.getAuthor().equals(user);
    }

    private Task checkAndGetTask(String taskId) {
        return taskRepository.findById(UUID.fromString(taskId))
                .orElseThrow(TaskNotFoundException::new);
    }

    private Comment checkAndGetComment(String commentId) {
        return commentRepository.findById(UUID.fromString(commentId))
                .orElseThrow(CommentNotFoundException::new);
    }
}
