package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.RoleDto;
import com.exadel.nikas_shop.entity.Role;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.mapper.RoleMapper;
import com.exadel.nikas_shop.repository.CategoryRepository;
import com.exadel.nikas_shop.repository.RoleRepository;
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
public class RoleServiceImpl implements RoleService {

    private final ExampleMatcher roleMatcher = ExampleMatcher.matching()
            .withIgnoreCase("id")
            .withMatcher("role_name", ignoreCase());
    @Autowired
    private RoleRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RoleMapper mapper;

    @Override
    public ResponseEntity<DataResponseDto<RoleDto>> getList() {
        List<RoleDto> roles = repository.findAll().stream()
                .map(role -> mapper.mapRoleToResponse(role))
                .collect(Collectors.toList());
        return ResponseEntity.ok
                (mapper.mapRolesToResponse
                        (roles.size(), roles));
    }

    @Override
    public ResponseEntity<RoleDto> getById(Long roleId) {
        Optional<Role> role = repository.findById(roleId);

        role.orElseThrow(() -> new EntityNotFoundException(String.format("Role with ID %d does not exist.", roleId)));

        return ResponseEntity.ok(mapper.mapRoleToResponse(role.get()));
    }

    @Override
    public ResponseEntity<RoleDto> create(RoleDto roleDto) {
        Role role = mapper.mapCreateToRole(roleDto);
        RoleDto roleResponseDto = mapper.mapRoleToResponse(repository.save(role));
        return new ResponseEntity<>(roleResponseDto, HttpStatus.CREATED);
    }

    @Override
    public void delete(Long roleId) {
        Optional<Role> role = repository.findById(roleId);

        role.orElseThrow(() -> new EntityNotFoundException(String.format("Role with ID %d does not exist.", roleId)));

        repository.deleteById(roleId);
    }

    @Override
    public ResponseEntity<RoleDto> update(Long roleId, RoleDto roleDto) {
        Role role = repository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Role with ID %d does not exist.", roleId)));
        mapper.mapUpdateToRole(role, roleDto);
        RoleDto roleResponseDto = mapper.mapRoleToResponse(repository.save(role));
        return ResponseEntity.ok(roleResponseDto);
    }

}
