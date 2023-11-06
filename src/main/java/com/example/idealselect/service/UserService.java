package com.example.idealselect.service;

import com.example.idealselect.dto.UserDto;
import com.example.idealselect.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    void join(String userId, String userName, String password, HttpServletRequest request);
    UserDto login(String userId, String password, HttpServletRequest request, HttpServletResponse response);
    void logOut(HttpServletRequest request, HttpServletResponse response);

}
