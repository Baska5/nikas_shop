package com.exadel.nikas_shop.repository;

import com.exadel.nikas_shop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select r from Role r where r.name = :roleName")
    Role findByRoleName(String roleName);

    @Query(value = "select r from Role r where r.id in :roleIds")
    List<Role> findByListOfIds(List<Long> roleIds);

}
