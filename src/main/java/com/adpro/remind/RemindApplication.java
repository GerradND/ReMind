package com.adpro.remind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.adpro.remind", "com.adpro.remind.service", "com.adpro.remind.service"})
@EnableJpaRepositories("com.adpro.remind.repository")
public class RemindApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemindApplication.class, args);
	}

}
