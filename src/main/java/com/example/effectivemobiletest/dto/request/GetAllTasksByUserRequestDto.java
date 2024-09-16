package com.example.effectivemobiletest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Запрос на получение всех задач по указанному пользователю (автор или исполнитель)")
public class GetAllTasksByUserRequestDto {
    @Schema(description = "Идентификатор пользователя", example = "2b5f2fee-e73f-467f-8399-235e9d4e473d")
    @NotNull(message = "Идентификатор пользователя не может быть null")
    @UUID(message = "Идентификатор пользователя должен быть в формате UUID")
    private String userId;
}
