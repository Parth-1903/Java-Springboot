package com.spring_intro_01.week_1_spring_intro;

import com.spring_intro_01.week_1_spring_intro.DB.DB;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Week1SpringIntroApplication {

	@Autowired
	private DB db;

	public static void main(String[] args) {
		SpringApplication.run(Week1SpringIntroApplication.class, args);

	}

	@PostConstruct
	public void method(){
		db.getDB();
	}

}
