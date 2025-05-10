package com.toeicstudyzone.service;

import com.toeicstudyzone.entity.Permission;
import java.util.List;

public interface PermissionService {
    List<Permission> getAllPermissions();
    Permission createPermission(String name, String description);
    void deletePermission(Integer id);
}