package com.week5_Security.Dto;

import com.week5_Security.entities.enums.Permission;
import com.week5_Security.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
	private String email;
	private String password;
	private String name;
	private Set<Role> roles;
	private Set<Permission> permissions;
}
