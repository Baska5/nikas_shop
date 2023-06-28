package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<DataResponseDto<UserDto>> getList();

    ResponseEntity<UserDto> getById(Long userId);

    ResponseEntity<UserDto> create(UserDto userCreateDto);

    void delete(Long userId);

    ResponseEntity<UserDto> update(Long userId, UserDto userUpdateDto);
}
