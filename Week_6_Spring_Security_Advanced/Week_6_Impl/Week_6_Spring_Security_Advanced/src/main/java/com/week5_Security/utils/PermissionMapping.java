package com.week5_Security.utils;

import com.week5_Security.entities.enums.Permission;
import com.week5_Security.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.week5_Security.entities.enums.Permission.*;
import static com.week5_Security.entities.enums.Role.*;

public class PermissionMapping {

	private static final Map<Role, Set<Permission>> map = Map.of(
			USER, Set.of(USER_VIEW, POST_VIEW),
			CREATOR, Set.of(USER_VIEW, POST_VIEW, USER_UPDATE, POST_UPDATE),
			ADMIN, Set.of(POST_CREATE,POST_DELETE,POST_UPDATE, USER_UPDATE, USER_DELETE, USER_CREATE)
	);

	public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
		return map.get(role).stream()
				.map(permission -> new SimpleGrantedAuthority(permission.name()))
				.collect(Collectors.toSet());
	}

}
