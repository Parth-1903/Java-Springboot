package com.week6HW.Week_6_Spring_Security_Advanced_HW.repository;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String username);
}
