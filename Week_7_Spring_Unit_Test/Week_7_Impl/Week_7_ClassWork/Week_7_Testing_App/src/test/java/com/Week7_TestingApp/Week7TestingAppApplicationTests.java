package com.Week7_TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
//@SpringBootTest
class Week7TestingAppApplicationTests {

	@BeforeEach
	void setUp(){
		log.info("Starting the method, setting up config");
	}

	@BeforeAll
	static void setUpOnce(){
		log.info("Setup Once...");
	}

	@AfterEach
	void tearDown(){
		log.info("Tearing down the method");
	}

	@AfterAll
	static void tearDownOnce(){
		log.info("Tearing down all....");
	}

	@Test
//	@Disabled
	void testNumberOne() {
		log.info("test 1 is run");
		int a = 5;
		int b = 3;

		int result = this.addTwoNumbers(a,b);

//		Assertions.assertEquals(, result);

//		it gives you chaining kind of for better understand
//		Assertions.assertThat(result)
//				.isEqualTo(8)
//				.isCloseTo(8, Offset.offset(1));

//		assertThat("Apple")
//				.isEqualTo("Apple")
//				.startsWith("App")
//				.endsWith("le")
//				.hasSize(5);
	}

	@Test
//	@DisplayName("displayNameTwo")
	void testDivideTwoNumbers_whenDenominatorIsZero_ThenArithmeticException(){
		log.info("test 2 is run");
		int a = 5;
		int b = 0;

		assertThatThrownBy(() -> divideTwoNumbers(a, b))
				.isInstanceOf(ArithmeticException.class)
				.hasMessage("Tried to divide by zero");

//		double result = divideTwoNumbers(a,b);
	}

	int addTwoNumbers(int a, int b){
		return a+b;
	}

	double divideTwoNumbers(int a, int b){
		try{
			return a / b;
		} catch (ArithmeticException e){
			log.error("Arithmetic exception occurred: "+e.getLocalizedMessage());
			throw new ArithmeticException("Tried to divide by zero");
		}
	}
}
