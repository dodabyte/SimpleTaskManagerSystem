package com.example.effectivemobiletest.dto.response;

import com.example.effectivemobiletest.entity.enums.Priority;
import com.example.effectivemobiletest.entity.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Ответ с данными о задаче")
public class TaskResponseDto {
    @Schema(description = "Идентификатор задачи", example = "a474a9e2-ee33-4bc2-bdc5-fe88ecdfb3a3")
    private String taskId;

    @Schema(description = "Идентификатор автора", example = "2b5f2fee-e73f-467f-8399-235e9d4e473d")
    private String authorId;

    @Schema(description = "Идентификатор исполнителя", example = "2b5f2fee-e73f-467f-8399-235e9d4e473d")
    private String executorId;

    @Schema(description = "Заголовок задачи", example = "Багфикс TaskService")
    private String title;

    @Schema(description = "Описание", example = "Для исправления данного бага можно обратиться к документации...")
    private String description;

    @Schema(description = "Статус", example = "IN_PROGRESS")
    private Status status;

    @Schema(description = "Приоритет", example = "MEDIUM")
    private Priority priority;
}
