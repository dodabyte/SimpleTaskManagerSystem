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
@Schema(description = "Запрос на аутентификацию")
public class SignInRequestDto {
    @Schema(description = "Адрес электронной почты", example = "user@example.com")
    @Size(min = 3, max = 255, message = "Адрес электронной почты должен содержать не менее 3 и не более 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Пароль", example = "h4&4-df?m-dg4_1!45")
    @Size(min = 8, max = 30, message = "Длина пароля должна быть не менее 8 и не более 30 символов")
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}
