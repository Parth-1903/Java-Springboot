package com.week6HW.Week_6_Spring_Security_Advanced_HW.repository;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.Session;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
	List<Session> findByUser(User user);

	boolean existsByRefreshToken(String refreshToken);

	void deleteByRefreshToken(String refreshToken);

	Optional<Session> findByRefreshToken(String refreshToken);
}
