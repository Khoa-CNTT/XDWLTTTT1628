package com.toeicstudyzone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserRoleId implements Serializable {

    @Column(name = "user_id")
    private Long userId; 

    @Column(name = "role_id")
    private Integer roleId; 
}