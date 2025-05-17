package com.toeicstudyzone.security;

import com.toeicstudyzone.entity.Permission;
import com.toeicstudyzone.entity.RolePermission;
import com.toeicstudyzone.entity.User;
import com.toeicstudyzone.entity.UserRole;
import com.toeicstudyzone.repository.RolePermissionRepository;
import com.toeicstudyzone.repository.UserRepository;
import com.toeicstudyzone.repository.UserRoleRepository;
// import com.toeicstudyzone.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    // @Autowired
    // private PermissionService permissionService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String username = authentication.getName();
        Long userId = getUserIdFromUsername(username);
        if (userId == null) {
            return false;
        }

        List<UserRole> userRoles = userRoleRepository.findById_UserId(userId);
        if (userRoles.isEmpty()) {
            return false;
        }

        for (UserRole userRole : userRoles) {
            Integer roleId = userRole.getId().getRoleId();
            List<RolePermission> rolePermissions = rolePermissionRepository.findById_Role(roleId);

            for (RolePermission rolePermission : rolePermissions) {
                Permission perm = rolePermission.getPermission();
                if (perm != null && perm.getName() != null && perm.getName().equals(permission.toString())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return hasPermission(authentication, null, permission);
    }

    private Long getUserIdFromUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getId)
                .orElse(null);
    }
}