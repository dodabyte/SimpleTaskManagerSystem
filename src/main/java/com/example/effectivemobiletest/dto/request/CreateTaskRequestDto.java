package com.example.effectivemobiletest.dto.request;

import com.example.effectivemobiletest.entity.enums.Priority;
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
@Schema(description = "Запрос на создание задачи")
public class CreateTaskRequestDto {
    @Schema(description = "Идентификатор исполнителя", example = "2b5f2fee-e73f-467f-8399-235e9d4e473d")
    @Nullable
    @UUID(message = "Идентификатор исполнителя должен быть в формате UUID")
    private String executorId;

    @Schema(description = "Заголовок задачи", example = "Багфикс TaskService")
    @NotNull(message = "Заголовок задачи не может быть null")
    @NotEmpty(message = "Заголовок задачи не может быть пустым")
    private String title;

    @Schema(description = "Описание", example = "Для исправления данного бага можно обратиться к документации...")
    @Nullable
    private String description;

    @Schema(description = "Приоритет", example = "MEDIUM")
    @Nullable
    private Priority priority;
}
