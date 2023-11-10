package com.example.idealselect.service;

import com.example.idealselect.dto.UserDto;
import com.example.idealselect.entity.User;
import com.example.idealselect.exception.CustomException;
import com.example.idealselect.exception.ErrorCode;
import com.example.idealselect.repository.UserMapper;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final SessionManager sessionManager;
    @Override
    public void join(String userId, String userName, String password) {
        Optional<User> findUser = userMapper.findByUserId(userId);
        if(findUser.isPresent())
            throw new CustomException(ErrorCode.ALREADY_EXIST_USERID);

        User user = User.builder().userId(userId).userName(userName).password(password).build();
        userMapper.save(user);
    }

    @Override
    public void login(String userId, String password, HttpServletRequest request, HttpServletResponse response) {
        Optional<User> findUser = userMapper.findByUserId(userId);
        if(findUser.isPresent()){
            if(findUser.get().getPassword().equals(password)) {
                sessionManager.createSession(findUser.get(), response);
            } else throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
        } else throw new CustomException(ErrorCode.INVALID_USERID);

    }

    @Override
    public void logOut(HttpServletRequest request, HttpServletResponse response) {

    }

}
