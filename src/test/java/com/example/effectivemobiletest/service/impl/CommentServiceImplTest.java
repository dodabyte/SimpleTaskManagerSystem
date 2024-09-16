package com.example.effectivemobiletest.service.impl;

import com.example.effectivemobiletest.data.TestDataFactory;
import com.example.effectivemobiletest.data.TestDtoFactory;
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
import com.example.effectivemobiletest.repository.CommentRepository;
import com.example.effectivemobiletest.repository.TaskRepository;
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
class CommentServiceImplTest {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    int page;
    int pageSize;
    Pageable pageable;

    Page<Comment> commentPage;
    List<Comment> commentList;
    User user;
    Task task;
    Comment addedComment;
    Comment updatedComment;

    GetAllCommentsOnTaskRequestDto getAllCommentsOnTaskRequestDto;
    AddCommentOnTaskRequestDto addCommentOnTaskRequestDto;
    UpdateCommentOnTaskRequestDto updateCommentOnTaskRequestDto;
    DeleteCommentFromTaskRequestDto deleteCommentFromTaskRequestDto;

    CommentResponseDto addCommentResponseDto;
    CommentResponseDto updateCommentResponseDto;
    InfoMessageResponseDto deleteCommentInfoMessageResponseDto;
    List<CommentResponseDto> commentResponseDtoList;

    @BeforeEach
    void setUp() {
        page = TestDataFactory.PAGE;
        pageSize = TestDataFactory.PAGE_SIZE;
        pageable = TestDataFactory.PAGEABLE;

        commentPage = TestDataFactory.createPageComments();
        commentList = TestDataFactory.createListComments();
        user = TestDataFactory.createUser2();
        task = TestDataFactory.createTask();
        addedComment = TestDataFactory.createComment();
        updatedComment = TestDataFactory.createComment();
        updatedComment.setText("Возможно, я смогу помочь тебе с задачей, но не факт)))");
        updatedComment.setModificationTime(updatedComment.getCommentTime().plusDays(1));

        getAllCommentsOnTaskRequestDto = TestDtoFactory.createGetAllCommentsOnTaskRequestDto();
        addCommentOnTaskRequestDto = TestDtoFactory.createAddCommentOnTaskRequestDto();
        updateCommentOnTaskRequestDto = TestDtoFactory.createUpdateCommentOnTaskRequestDto();
        deleteCommentFromTaskRequestDto = TestDtoFactory.createDeleteCommentFromTaskRequestDto();

        addCommentResponseDto = TestDtoFactory.createAddCommentResponseDto();
        updateCommentResponseDto = TestDtoFactory.createUpdateCommentResponseDto();
        deleteCommentInfoMessageResponseDto = TestDtoFactory.createInfoMessageResponseDtoForDeleteComment();
        commentResponseDtoList = TestDtoFactory.createListCommentResponseDto();
    }

    @Test
    public void getAllCommentsInTask_whenGetIsOk_thenReturnCommentResponseDtoList() {
        Mockito.when(commentRepository.findAllByTaskId(TestDataFactory.TASK_ID, pageable))
                .thenReturn(commentPage);

        List<CommentResponseDto> result = commentService.getAllCommentsInTask(getAllCommentsOnTaskRequestDto, page, pageSize);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(commentResponseDtoList, result);

        Mockito.verify(commentRepository, Mockito.times(1)).findAllByTaskId(ArgumentMatchers.any(UUID.class),
                ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void addCommentOnTask_whenAddIsOk_thenReturnCommentResponseDto() {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.of(task));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(addedComment);

        CommentResponseDto result = commentService.addCommentOnTask(addCommentOnTaskRequestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(addCommentResponseDto, result);

        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(commentRepository, Mockito.times(1)).save(ArgumentMatchers.any(Comment.class));
    }

    @Test
    public void addCommentOnTask_whenTaskNotFound_thenThrowTaskNotFoundException() {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(TaskNotFoundException.class,
                () -> commentService.addCommentOnTask(addCommentOnTaskRequestDto));

        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(commentRepository, Mockito.never()).save(ArgumentMatchers.any(Comment.class));
    }

    @Test
    public void updateCommentOnTask_whenUpdateIsOk_thenReturnCommentResponseDto() {
        Mockito.when(commentRepository.findById(TestDataFactory.COMMENT_ID)).thenReturn(Optional.of(addedComment));
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.of(task));
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(updatedComment);

        CommentResponseDto result = commentService.updateCommentOnTask(updateCommentOnTaskRequestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updateCommentResponseDto, result);

        Mockito.verify(commentRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(commentRepository, Mockito.times(1)).save(ArgumentMatchers.any(Comment.class));
    }

    @Test
    public void updateCommentOnTask_whenCommentNotFound_thenThrowCommentNotFoundException() {
        Mockito.when(commentRepository.findById(TestDataFactory.COMMENT_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(CommentNotFoundException.class,
                () -> commentService.updateCommentOnTask(updateCommentOnTaskRequestDto));

        Mockito.verify(commentRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.never()).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(userService, Mockito.never()).getCurrentUser();
        Mockito.verify(commentRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    public void updateCommentOnTask_whenTaskNotFound_thenThrowTaskNotFoundException() {
        Mockito.when(commentRepository.findById(TestDataFactory.COMMENT_ID)).thenReturn(Optional.of(addedComment));
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(TaskNotFoundException.class,
                () -> commentService.updateCommentOnTask(updateCommentOnTaskRequestDto));

        Mockito.verify(commentRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(userService, Mockito.never()).getCurrentUser();
        Mockito.verify(commentRepository, Mockito.never()).save(ArgumentMatchers.any(Comment.class));
    }

    @Test
    public void updateCommentOnTask_whenClientEditingSomeoneElseComment_thenThrowUpdateCommentAnotherUserException() {
        task.setAuthor(new User());

        Mockito.when(commentRepository.findById(TestDataFactory.COMMENT_ID)).thenReturn(Optional.of(addedComment));
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.of(task));
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Assertions.assertThrows(UpdateCommentAnotherUserException.class,
                () -> commentService.updateCommentOnTask(updateCommentOnTaskRequestDto));

        Mockito.verify(commentRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(commentRepository, Mockito.never()).save(ArgumentMatchers.any(Comment.class));
    }

    @Test
    public void deleteCommentFromTask_whenClientEditingSomeoneElseComment_thenThrowDeleteCommentAnotherUserException() {
        task.setAuthor(new User());

        Mockito.when(commentRepository.findById(TestDataFactory.COMMENT_ID)).thenReturn(Optional.of(addedComment));
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.of(task));
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Assertions.assertThrows(DeleteCommentAnotherUserException.class,
                () -> commentService.deleteCommentFromTask(deleteCommentFromTaskRequestDto));

        Mockito.verify(commentRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(commentRepository, Mockito.never()).delete(ArgumentMatchers.any(Comment.class));
    }

    @Test
    public void deleteCommentFromTask_whenDeleteIsOk_thenReturnInfoMessageResponseDto() {
        Mockito.when(commentRepository.findById(TestDataFactory.COMMENT_ID)).thenReturn(Optional.of(addedComment));
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.of(task));
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        InfoMessageResponseDto result = commentService.deleteCommentFromTask(deleteCommentFromTaskRequestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(deleteCommentInfoMessageResponseDto, result);

        Mockito.verify(commentRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(commentRepository, Mockito.times(1)).delete(ArgumentMatchers.any(Comment.class));
    }

    @Test
    public void deleteCommentFromTask_whenTaskNotFound_thenThrowTaskNotFoundException() {
        Mockito.when(commentRepository.findById(TestDataFactory.COMMENT_ID)).thenReturn(Optional.of(addedComment));
        Mockito.when(taskRepository.findById(TestDataFactory.TASK_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(TaskNotFoundException.class,
                () -> commentService.deleteCommentFromTask(deleteCommentFromTaskRequestDto));

        Mockito.verify(commentRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(userService, Mockito.never()).getCurrentUser();
        Mockito.verify(commentRepository, Mockito.never()).delete(ArgumentMatchers.any(Comment.class));
    }

    @Test
    public void deleteCommentFromTask_whenCommentNotFound_thenThrowTaskNotFoundException() {
        Mockito.when(commentRepository.findById(TestDataFactory.COMMENT_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(CommentNotFoundException.class,
                () -> commentService.deleteCommentFromTask(deleteCommentFromTaskRequestDto));

        Mockito.verify(commentRepository, Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(taskRepository, Mockito.never()).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(userService, Mockito.never()).getCurrentUser();
        Mockito.verify(commentRepository, Mockito.never()).delete(ArgumentMatchers.any(Comment.class));
    }
}