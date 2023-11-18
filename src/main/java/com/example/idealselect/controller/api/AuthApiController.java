package com.example.idealselect.controller.api;

import com.example.idealselect.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response){
        userService.login(userId, password, request, response);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity join(@RequestParam String userId, @RequestParam String userName, @RequestParam String password){
        userService.join(userId, userName, password);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/logOut")
    public ResponseEntity logOut(HttpServletRequest request, HttpServletResponse response) {
        userService.logOut(request, response);
        return new ResponseEntity(HttpStatus.OK);
    }
}
