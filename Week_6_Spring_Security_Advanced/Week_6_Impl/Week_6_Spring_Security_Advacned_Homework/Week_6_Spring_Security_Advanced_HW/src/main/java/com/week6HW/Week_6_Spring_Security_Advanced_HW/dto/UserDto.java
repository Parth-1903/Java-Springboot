package com.week6HW.Week_6_Spring_Security_Advanced_HW.dto;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;

	private String name;

	private String email;

	private String password;

	private SubscriptionDto subscription;

	private Integer maxLogin;

}
