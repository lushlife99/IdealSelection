package com.example.idealselect;

import com.example.idealselect.repository.IdealMapper;
import com.example.idealselect.repository.IdealSelectionMapper;
import com.example.idealselect.repository.UserMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class IdealSelectApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdealSelectApplication.class, args);
    }

    @Bean
    public DataInit DataInit(UserMapper userMapper, IdealMapper idealMapper, IdealSelectionMapper selectionMapper){
        return new DataInit(userMapper, idealMapper, selectionMapper);
    }

}