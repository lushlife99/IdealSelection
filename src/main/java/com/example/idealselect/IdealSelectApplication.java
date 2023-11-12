package com.example.idealselect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class IdealSelectApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdealSelectApplication.class, args);
    }

//    @Bean
//    public DataInit testDataInit() {
//        return new TestDataInit();
//    }
}