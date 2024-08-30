package com.week6HW.Week_6_Spring_Security_Advanced_HW.repository;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
