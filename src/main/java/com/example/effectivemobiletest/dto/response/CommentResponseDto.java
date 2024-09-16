package com.example.effectivemobiletest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Ответ с данными о пользователе")
@EqualsAndHashCode
public class CommentResponseDto {
    @Schema(description = "Идентификатор комментария", example = "34b13ef6-3abf-4f20-bcfb-7d9562a2022d")
    String commentId;

    @Schema(description = "Идентификатор задачи", example = "a474a9e2-ee33-4bc2-bdc5-fe88ecdfb3a3")
    String taskId;

    @Schema(description = "Идентификатор автора", example = "2b5f2fee-e73f-467f-8399-235e9d4e473d")
    String authorId;

    @Schema(description = "Текст комментария", example = "Помогу с выполнением ;)")
    String text;

    @Schema(description = "Дата и время комментирования", example = "2024-08-29T19:17:31.664972")
    LocalDateTime commentTime;

    @Schema(description = "Дата и время последнего редактирования комментария", example = "2024-08-29T20:01:14.432123")
    LocalDateTime modificationTime;
}
