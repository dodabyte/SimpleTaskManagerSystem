package com.example.effectivemobiletest.mapper;

import com.example.effectivemobiletest.dto.response.UserResponseDto;
import com.example.effectivemobiletest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "registrationDate", source = "registrationDate")
    @Mapping(target = "role", source = "role")
    UserResponseDto fromUserToUserResponseDto(User user);
}
