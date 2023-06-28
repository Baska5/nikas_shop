package com.exadel.nikas_shop.controller;

import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.UserDto;
import com.exadel.nikas_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @PreAuthorize("@securityAuthService.hasAccess('admin')")
    ResponseEntity<DataResponseDto<UserDto>> getList() {
        return userService.getList();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("@securityAuthService.isUserOwner(#userId)")
    ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) {
        return userService.getById(userId);
    }

    @PostMapping()
    @PreAuthorize("permitAll()")
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userCreateDto) {
        return userService.create(userCreateDto);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("@securityAuthService.isUserOwner(#userId)")
    void deleteById(@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("@securityAuthService.isUserOwner(#userId)")
    ResponseEntity<UserDto> updateUser(@PathVariable("userId") Long userId,
                                       @Valid @RequestBody UserDto userUpdateDto) {
        return userService.update(userId, userUpdateDto);
    }
}
