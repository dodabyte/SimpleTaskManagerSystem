package com.example.effectivemobiletest.dto.request;

import com.example.effectivemobiletest.entity.enums.Priority;
import com.example.effectivemobiletest.entity.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Запрос на редактирование задачи")
public class UpdateTaskRequestDto {
    @Schema(description = "Идентификатор задачи", example = "a474a9e2-ee33-4bc2-bdc5-fe88ecdfb3a3")
    @NotNull(message = "Идентификатор задачи не может быть null")
    @UUID(message = "Идентификатор задачи должен быть в формате UUID")
    private String taskId;

    @Schema(description = "Идентификатор исполнителя", example = "2b5f2fee-e73f-467f-8399-235e9d4e473d")
    @Nullable
    @UUID(message = "Идентификатор исполнителя должен быть в формате UUID")
    private String executorId;

    @Schema(description = "Заголовок задачи", example = "Багфикс TaskService")
    @Nullable
    @NotEmpty(message = "Заголовок задачи не может быть пустым")
    private String title;

    @Schema(description = "Описание", example = "Для исправления данного бага можно обратиться к документации...")
    @Nullable
    private String description;

    @Schema(description = "Статус", example = "IN_PROGRESS")
    @Nullable
    private Status status;

    @Schema(description = "Приоритет", example = "MEDIUM")
    @Nullable
    private Priority priority;
}
