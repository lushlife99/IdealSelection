package com.example.idealselect.repository;

import com.example.idealselect.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;


@Mapper
public interface UserMapper {

    void save(User user);
    void update(@Param("id") Long id, @Param("updateParam")User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUserId(String userId);

}
