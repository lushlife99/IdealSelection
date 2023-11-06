package com.example.idealselect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {


    @GetMapping("/")
    public String loginPage(){

        return "login";
    }

    @GetMapping("/join")
    public String joinPage(){

        return "join";
    }

}
