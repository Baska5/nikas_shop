package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.RoleDto;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    ResponseEntity<DataResponseDto<RoleDto>> getList();

    ResponseEntity<RoleDto> getById(Long roleId);

    ResponseEntity<RoleDto> create(RoleDto roleDto);

    void delete(Long roleId);

    ResponseEntity<RoleDto> update(Long roleId, RoleDto roleUpdateDto);
}
