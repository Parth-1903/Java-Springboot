package com.spring_intro_01.week_1_spring_intro.DB;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
@Component
@ConditionalOnProperty(name = "deploy.name", havingValue = "development")
public class DevDB implements DB {
	public void getDB(){
		System.out.println("Dev DB");
	}
}
