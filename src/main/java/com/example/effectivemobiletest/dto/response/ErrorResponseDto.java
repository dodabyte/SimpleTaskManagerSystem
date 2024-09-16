package com.example.effectivemobiletest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(description = "Ответ с ошибкой")
public class ErrorResponseDto {
    @Schema(description = "Дата и время", example = "2024-08-29T19:43:17.585")
    private String timestamp;

    @Schema(description = "Статус", example = "404")
    private int status;

    @Schema(description = "Ошибка", example = "Пользователь не найден")
    private String error;

    @Schema(description = "Путь (URL)", example = "/tasks/author")
    private String path;

    public ErrorResponseDto(String message, HttpStatus status, WebRequest request){
        this.timestamp = LocalDateTime.now().toString();
        this.status = status.value();
        this.error = message;
        this.path = ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}
