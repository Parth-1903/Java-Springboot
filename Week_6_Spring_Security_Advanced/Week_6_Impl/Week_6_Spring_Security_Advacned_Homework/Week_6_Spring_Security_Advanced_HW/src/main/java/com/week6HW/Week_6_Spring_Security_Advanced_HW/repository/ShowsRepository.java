package com.week6HW.Week_6_Spring_Security_Advanced_HW.repository;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.Shows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowsRepository extends JpaRepository<Shows, Long> {
	Optional<Shows> findByMovieNameIgnoreCase(String movieName);
	List<Shows> findByMovieNameContainingIgnoreCase(String movieName);

	boolean existsByMovieNameIgnoreCase(String movieName);

	void deleteByMovieName(String movieName);
}
