package com.tinvio.accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {ContextStackAutoConfiguration.class})
@EnableAsync
@ComponentScan(basePackages = {"com.tinvio.*"})
@EnableJpaRepositories(basePackages = {"com.tinvio.accounting.repository"})
@EnableFeignClients(basePackages = {"com.tinvio.accounting.proxy"})
@EnableTransactionManagement
@EnableRetry
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
