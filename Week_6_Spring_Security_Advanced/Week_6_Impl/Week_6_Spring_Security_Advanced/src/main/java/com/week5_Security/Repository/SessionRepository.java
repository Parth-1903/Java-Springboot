package com.week5_Security.Repository;

import com.week5_Security.entities.Session;
import com.week5_Security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
	List<Session> findByUser(User user);

	Optional<Session> findByRefreshToken(String refreshToken);
}
