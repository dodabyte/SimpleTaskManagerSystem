package com.example.effectivemobiletest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Ответ с информационным сообщением")
public class InfoMessageResponseDto {
    @Schema(description = "Идентификатор объекта", example = "a474a9e2-ee33-4bc2-bdc5-fe88ecdfb3a3")
    private String id;

    @Schema(description = "Тип объекта", example = "COMMENT")
    private String type;

    @Schema(description = "Сообщение", example = "Задача успешно удалена")
    private String message;
}
