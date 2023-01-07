package com.eminyilmazz.smoketracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.eminyilmazz")
@EntityScan(basePackages = "com.eminyilmazz.smoketracker.entity")
@EnableJpaRepositories(basePackages = "com.eminyilmazz.smoketracker.repository")

public class SmokeTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmokeTrackerApplication.class, args);
    }

}
