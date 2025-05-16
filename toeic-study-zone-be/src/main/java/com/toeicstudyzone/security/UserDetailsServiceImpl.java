package com.toeicstudyzone.security;

import com.toeicstudyzone.entity.User;
import com.toeicstudyzone.entity.UserRole;
import com.toeicstudyzone.entity.UserRoleId;
import com.toeicstudyzone.enums.UserStatus;
import com.toeicstudyzone.repository.RoleRepository;
import com.toeicstudyzone.repository.UserRepository;
import com.toeicstudyzone.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Loading user with email: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        System.out.println("User found: " + user.getEmail() + ", Status: " + user.getStatus());
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new UsernameNotFoundException("User account is not active: " + email);
        }

        List<UserRole> userRoles = userRoleRepository.findById_UserIdWithRole(user.getId());
        System.out.println("User roles found: " + userRoles.size() + ", Roles: " + userRoles);
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (userRoles.isEmpty()) {
            System.out.println("No roles found for user, assigning default role: ROLE_USER");
            UserRole defaultRole = new UserRole();
            defaultRole.setId(new UserRoleId(user.getId(), 1));
            defaultRole.setUser(user);
            defaultRole.setRole(roleRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Default role not found")));
            userRoleRepository.save(defaultRole);
            userRoles.add(defaultRole);
            System.out.println("Added default role: " + defaultRole.getRole().getName());
        }

        for (UserRole userRole : userRoles) {
            if (userRole.getRole() == null) {
                System.out.println("Role is null for userRole: " + userRole);
                continue;
            }
            String roleName = userRole.getRole().getName();
            if (roleName == null) {
                System.out.println("Role name is null for userRole: " + userRole);
                continue;
            }
            // Không thêm tiền tố "ROLE_" vì roleName đã có tiền tố (ví dụ: ROLE_ADMIN)
            authorities.add(new SimpleGrantedAuthority(roleName));
            System.out.println("Added authority: " + roleName);
        }

        System.out.println("Final authorities: " + authorities);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                true, true, true, true,
                authorities);
    }
}