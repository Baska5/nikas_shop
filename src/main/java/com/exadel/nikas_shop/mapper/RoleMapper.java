package com.exadel.nikas_shop.mapper;

import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.RoleDto;
import com.exadel.nikas_shop.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface RoleMapper {
    default Long roleToId(Role role) {
        return role.getId();
    }

    List<Long> rolesToIds(List<Role> roles);

    default Role idToRole(Long id) {
        Role role = new Role();
        role.setId(id);
        return role;
    }

    List<Role> idsToRoles(List<Long> ids);

    RoleDto mapRoleToResponse(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    Role mapCreateToRole(RoleDto roleDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    void mapUpdateToRole(@MappingTarget Role role, RoleDto roleDto);

    @Mapping(target = "data", source = "list")
    @Mapping(target = "size", source = "size")
    DataResponseDto<RoleDto> mapRolesToResponse(Integer size, List<RoleDto> list);
}
