package com.sergio.gym;

import org.springframework.boot.SpringApplication;

public class TestGymApplication {

	public static void main(String[] args) {
		SpringApplication.from(GymApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
