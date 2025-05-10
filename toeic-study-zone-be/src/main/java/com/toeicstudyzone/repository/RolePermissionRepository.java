package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.RolePermission;
import com.toeicstudyzone.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {
    List<RolePermission> findById_Role(Integer role);
}