package com.week6HW.Week_6_Spring_Security_Advanced_HW.service;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

	private final SubscriptionRepository subscriptionRepository;
	private final ModelMapper modelMapper;



}
