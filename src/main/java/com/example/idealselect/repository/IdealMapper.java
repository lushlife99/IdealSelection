package com.example.idealselect.repository;

import com.example.idealselect.entity.Ideal;
import com.example.idealselect.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;


@Mapper
public interface IdealMapper {
    void save(Ideal ideal);
    void update(Long id, Ideal ideal);
    Optional<Ideal> findById(Long id);
    void deleteById(Long id);
    List<Ideal> findAllBySelectionId(Long selectionId);


    void saveAll(List<Ideal> idealList);
    void deleteAll(List<Ideal> idealList);
}
