package com.bester.platform.platformchain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.bester.platform.platformchain.dao")
public class PlatformChainApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformChainApplication.class, args);
	}
}
