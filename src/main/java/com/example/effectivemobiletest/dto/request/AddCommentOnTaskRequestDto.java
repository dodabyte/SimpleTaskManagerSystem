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
@Schema(description = "Запрос на добавление комментария к задаче")
public class AddCommentOnTaskRequestDto {
    @Schema(description = "Идентификатор задачи", example = "a474a9e2-ee33-4bc2-bdc5-fe88ecdfb3a3")
    @NotNull(message = "Идентификатор задачи не может быть null")
    @UUID(message = "Идентификатор задачи должен быть в формате UUID")
    private String taskId;

    @Schema(description = "Комментарий к задаче", example = "Помогу с выполнением ;)")
    @NotNull(message = "Комментарий к задаче не может быть null")
    @NotEmpty(message = "Комментарий к задаче не может быть пустым")
    String comment;
}
