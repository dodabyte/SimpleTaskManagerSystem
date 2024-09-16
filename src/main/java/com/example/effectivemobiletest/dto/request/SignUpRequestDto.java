package com.example.effectivemobiletest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Запрос на регистрацию")
public class SignUpRequestDto {
    @Schema(description = "Имя пользователя", example = "User")
    @Size(min = 4, max = 50, message = "Имя пользователя должно содержать не менее 4 и не более 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @Schema(description = "Полное имя пользователя", example = "Иванов Иван Иванович")
    @Size(min = 4, max = 100, message = "Полное имя пользователя должно содержать не менее 4 и не более 100 символов")
    @NotBlank(message = "Полное имя пользователя не может быть пустым")
    private String fullName;

    @Schema(description = "Адрес электронной почты", example = "user@example.com")
    @Size(min = 3, max = 255, message = "Адрес электронной почты должен содержать не менее 3 и не более 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Пароль", example = "h4&4-df?m-dg4_1!45")
    @Size(min = 8, max = 30, message = "Длина пароля должна быть не менее 8 и не более 30 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
