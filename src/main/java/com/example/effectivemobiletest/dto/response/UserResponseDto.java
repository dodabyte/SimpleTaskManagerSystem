package com.example.effectivemobiletest.dto.response;

import com.example.effectivemobiletest.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Ответ с данными о пользователе")
public class UserResponseDto {
    @Schema(description = "Идентификатор пользователя", example = "2b5f2fee-e73f-467f-8399-235e9d4e473d")
    String userId;

    @Schema(description = "Имя пользователя", example = "User")
    String username;

    @Schema(description = "Полное имя пользователя", example = "Иванов Иван Иванович")
    String fullName;

    @Schema(description = "Электронная почта", example = "user@example.com")
    String email;

    @Schema(description = "Дата регистрации", example = "2024-08-29T18:52:26.918578")
    LocalDateTime registrationDate;

    @Schema(description = "Роль", example = "ROLE_USER")
    Role role;
}
