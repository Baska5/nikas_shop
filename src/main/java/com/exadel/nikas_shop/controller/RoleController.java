package com.exadel.nikas_shop.controller;

import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.RoleDto;
import com.exadel.nikas_shop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("role")
@RequiredArgsConstructor
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @PreAuthorize("@securityAuthService.hasAccess('admin')")
    ResponseEntity<DataResponseDto<RoleDto>> getList() {
        return roleService.getList();
    }

    @GetMapping("/{roleId}")
    @PreAuthorize("@securityAuthService.hasAccess('admin')")
    ResponseEntity<RoleDto> getRoleById(@PathVariable("roleId") Long roleId) {
        return roleService.getById(roleId);
    }

    @PostMapping()
    @PreAuthorize("@securityAuthService.hasAccess('admin')")
    ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleDto roleDto) {
        return roleService.create(roleDto);
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize("@securityAuthService.hasAccess('admin')")
    void deleteById(@PathVariable("roleId") Long roleId) {
        roleService.delete(roleId);
    }

    @PutMapping("/{roleId}")
    @PreAuthorize("@securityAuthService.hasAccess('admin')")
    ResponseEntity<RoleDto> updateRole(@PathVariable("roleId") Long roleId,
                                               @Valid @RequestBody RoleDto roleDto) {
        return roleService.update(roleId, roleDto);
    }
}
