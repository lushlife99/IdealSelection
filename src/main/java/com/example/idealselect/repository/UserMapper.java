package com.example.idealselect.repository;

import com.example.idealselect.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Mapper
public interface UserMapper {
    void save(User user);
    void update(Long id, User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUserId(String userId);
    void deleteById(Long id);

}
