package com.example.idealselect.service;

import com.example.idealselect.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public void join(String userId, String userName, String password, HttpServletRequest request) {
        System.out.println("UserServiceImpl.join");
    }

    @Override
    public UserDto login(String userId, String password, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public void logOut(HttpServletRequest request, HttpServletResponse response) {

    }
}
