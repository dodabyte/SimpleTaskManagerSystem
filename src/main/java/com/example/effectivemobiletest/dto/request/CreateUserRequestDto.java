package com.example.effectivemobiletest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Запрос на создание пользователя")
public class CreateUserRequestDto {
    @Schema(description = "Имя пользователя", example = "User")
    @NotNull(message = "Имя пользователя не может быть null")
    @NotEmpty(message = "Имя пользователя не может быть пустым")
    private String username;

    @Schema(description = "Полное имя пользователя", example = "Иванов Иван Иванович")
    @NotNull(message = "Полное имя пользователя не может быть null")
    @NotEmpty(message = "Полное имя пользователя не может быть пустым")
    private String fullName;

    @Schema(description = "Электронная почта", example = "user@example.com")
    @NotNull(message = "Электронная почта не может быть null")
    @NotEmpty(message = "Электронная почта не может быть пустой")
    private String email;

    @Schema(description = "Пароль", example = "14-ed$f35-sd^f3-!dsf")
    @NotNull(message = "Пароль не может быть null")
    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;
}
