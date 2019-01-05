package com.bester.platform.platformchain;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author liuwen
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableDubboConfiguration
public class PlatformChainApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformChainApplication.class, args);
	}
}
