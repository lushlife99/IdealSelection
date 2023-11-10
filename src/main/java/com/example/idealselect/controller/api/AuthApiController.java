package com.example.idealselect.controller.api;

import com.example.idealselect.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final UserServiceImpl userService;

    @PostMapping("/login")
    public void login(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response){
        userService.login(userId, password, request, response);
    }

    @PostMapping("/join")
    public void join(@RequestParam String userId, @RequestParam String userName, @RequestParam String password){
        userService.join(userId, userName, password);
    }

    @GetMapping("/logOut")
    public void logOut(HttpServletRequest request, HttpServletResponse response) {
        userService.logOut(request, response);
    }
}
