package com.week_1_HW.Week_1_HW_CakeBakery;

import com.week_1_HW.Week_1_HW_CakeBakery.Services.CakeBaker;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Week1HwCakeBakeryApplication implements CommandLineRunner {
	@Autowired
	private CakeBaker cakeBaker;
	public static void main(String[] args) {
		SpringApplication.run(Week1HwCakeBakeryApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		cakeBaker.bakeCake();
	}
}
