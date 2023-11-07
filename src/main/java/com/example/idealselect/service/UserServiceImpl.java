package com.example.idealselect.service;

import com.example.idealselect.dto.UserDto;
import com.example.idealselect.entity.User;
import com.example.idealselect.repository.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    @Override
    public void join(String userId, String userName, String password, HttpServletRequest request) {
        User user = User.builder().userId(userId).userName(userName).password(password).build();
        userMapper.save(user);
    }

    @Override
    public UserDto login(String userId, String password, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public void logOut(HttpServletRequest request, HttpServletResponse response) {

    }

}
