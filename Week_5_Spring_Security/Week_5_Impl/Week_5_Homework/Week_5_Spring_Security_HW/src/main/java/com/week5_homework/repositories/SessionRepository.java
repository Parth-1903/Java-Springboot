package com.week5_homework.repositories;

import com.week5_homework.entities.Session;
import com.week5_homework.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

	Optional<Session> findByToken(String token);

	Optional<Session> findByUser(User user);
}
