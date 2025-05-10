package com.toeicstudyzone.controller;

import com.toeicstudyzone.dto.response.StatisticsResponse;
import com.toeicstudyzone.dto.response.UserResponse;
import com.toeicstudyzone.entity.Permission;
import com.toeicstudyzone.service.AdminService;
import com.toeicstudyzone.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/{userId}/assign-role/{roleId}")
    public ResponseEntity<String> assignRole(@PathVariable Long userId, @PathVariable Integer roleId) {
        adminService.assignRole(userId, roleId);
        return ResponseEntity.ok("Role assigned successfully");
    }

    @PostMapping("/users/{userId}/lock")
    public ResponseEntity<String> lockUserAccount(@PathVariable Long userId) {
        adminService.lockUserAccount(userId);
        return ResponseEntity.ok("User account locked successfully");
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> getUserStatistics() {
        StatisticsResponse response = adminService.getUserStatistics();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @PostMapping("/permissions")
    public ResponseEntity<Permission> createPermission(@RequestParam String name, @RequestParam String description) {
        Permission permission = permissionService.createPermission(name, description);
        return ResponseEntity.ok(permission);
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<String> deletePermission(@PathVariable Integer id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok("Permission deleted successfully");
    }
}