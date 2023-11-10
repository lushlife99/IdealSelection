package com.example.idealselect.controller;

import com.example.idealselect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping(value = {"/", "/login"})
    public String loginPage(){
        System.out.println("AuthController.loginPage");
        return "login";
    }

    @GetMapping("/join")
    public String joinPage(){

        return "join";
    }
}
