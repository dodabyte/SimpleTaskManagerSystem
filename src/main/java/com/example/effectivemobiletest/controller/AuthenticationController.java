package com.example.effectivemobiletest.controller;

import com.example.effectivemobiletest.dto.request.SignInRequestDto;
import com.example.effectivemobiletest.dto.request.SignUpRequestDto;
import com.example.effectivemobiletest.dto.response.ErrorResponseDto;
import com.example.effectivemobiletest.dto.response.JwtAuthenticationResponseDto;
import com.example.effectivemobiletest.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Аутентификация", description = "Взаимодействие с аутентификацией")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtAuthenticationResponseDto.class)
                            )}
                    ),
                    @ApiResponse(responseCode = "400", description = "UserAlreadyRegisteredException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 400,
                                              "error": "Пользователь с указанным именем пользователя или электронной почтой уже зарегистрирован",
                                              "path": "/auth/sign-up"
                                            }
                                            """)
                                    }
                            )}
                    )
            }
    )
    @PostMapping("/sign-up")
    public JwtAuthenticationResponseDto signUp(@RequestBody @Valid SignUpRequestDto request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtAuthenticationResponseDto.class)
                            )}
                    ),
                    @ApiResponse(responseCode = "404", description = "UserNotFoundException",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = {@ExampleObject("""
                                            {
                                              "timestamp": "2024-08-29T19:43:17.585",
                                              "status": 404,
                                              "error": "Пользователь с указанной электронной почтой не найден",
                                              "path": "/auth/sign-in"
                                            }
                                            """)
                                    }
                            )}
                    )
            }
    )
    @PostMapping("/sign-in")
    public JwtAuthenticationResponseDto signIn(@RequestBody @Valid SignInRequestDto request) {
        return authenticationService.signIn(request);
    }
}
