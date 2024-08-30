package com.week5_homework.repositories;

import com.week5_homework.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findByEmail(String email);

}
