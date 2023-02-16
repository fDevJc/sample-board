package com.jc.callbustask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CallbusTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(CallbusTaskApplication.class, args);
	}

}
