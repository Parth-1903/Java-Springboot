package com.week6HW.Week_6_Spring_Security_Advanced_HW.entities;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(unique = true)
	private String email;

	private String password;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Subscription subscription;

	@Column(name = "max_login")
	private Integer maxLogin;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Session> sessions;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
				.collect(Collectors.toSet());
	}

	@Override
	public String getUsername() {
		return this.email;
	}
}
