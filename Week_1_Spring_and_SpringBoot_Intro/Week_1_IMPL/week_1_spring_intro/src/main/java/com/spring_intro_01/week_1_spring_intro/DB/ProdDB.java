package com.spring_intro_01.week_1_spring_intro.DB;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "deploy.name", havingValue = "production")
public class ProdDB implements DB{
	@Override
	public void getDB() {
		System.out.println("Production DB");
	}
}
