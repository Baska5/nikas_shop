package com.exadel.nikas_shop.mapper;

import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.UserDto;
import com.exadel.nikas_shop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface UserMapper {

    default Long userToId(User user) {
        return user.getId();
    }

    UserDto mapUserToResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    User mapCreateToUser(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    void mapUpdateToUser(@MappingTarget User user, UserDto userDto);

    @Mapping(target = "data", source = "list")
    @Mapping(target = "size", source = "size")
    DataResponseDto<UserDto> mapUsersToResponse(Integer size, List<UserDto> list);
}
