package com.example.effectivemobiletest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Запрос на редактирование комментария к задаче")
public class UpdateCommentOnTaskRequestDto {
    @Schema(description = "Идентификатор комментария", example = "34b13ef6-3abf-4f20-bcfb-7d9562a2022d")
    @NotNull(message = "Идентификатор комментария не может быть null")
    @UUID(message = "Идентификатор комментария должен быть в формате UUID")
    private String commentId;

    @Schema(description = "Комментарий к задаче", example = "Помогу с выполнением ;)")
    @NotNull(message = "Комментарий к задаче не может быть null")
    @NotEmpty(message = "Комментарий к задаче не может быть пустым")
    String comment;
}
