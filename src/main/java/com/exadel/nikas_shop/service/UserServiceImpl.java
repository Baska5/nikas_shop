package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.UserDto;
import com.exadel.nikas_shop.entity.User;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.mapper.UserMapper;
import com.exadel.nikas_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public class UserServiceImpl implements UserService {

    private final ExampleMatcher emailMatcher = ExampleMatcher.matching()
            .withIgnoreCase("id")
            .withMatcher("email", ignoreCase());
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMapper mapper;

    @Override
    public ResponseEntity<DataResponseDto<UserDto>> getList() {
        List<UserDto> users = repository.findAll().stream()
                .map(user -> mapper.mapUserToResponse(user))
                .collect(Collectors.toList());
        return ResponseEntity.ok
                (mapper.mapUsersToResponse
                        (users.size(), users));
    }

    @Override
    public ResponseEntity<UserDto> getById(Long userId) {
        Optional<User> user = repository.findById(userId);

        user.orElseThrow(() -> new EntityNotFoundException(String.format("User with ID %d does not exist.", userId)));

        return ResponseEntity.ok(mapper.mapUserToResponse(user.get()));
    }

    @Override
    public ResponseEntity<UserDto> create(UserDto userDto) {
        User user = mapper.mapCreateToUser(userDto);
        UserDto userResponseDto = mapper.mapUserToResponse(repository.save(user));
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @Override
    public void delete(Long userId) {
        Optional<User> user = repository.findById(userId);

        user.orElseThrow(() -> new EntityNotFoundException(String.format("User with ID %d does not exist.", userId)));

        repository.deleteById(userId);
    }

    @Override
    public ResponseEntity<UserDto> update(Long userId, UserDto userDto) {
        User user = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User with ID %d does not exist.", userId)));
        mapper.mapUpdateToUser(user, userDto);
        UserDto userResponseDto = mapper.mapUserToResponse(repository.save(user));
        return ResponseEntity.ok(userResponseDto);
    }

}
