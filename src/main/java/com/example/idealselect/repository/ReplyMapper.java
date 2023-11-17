package com.example.idealselect.repository;

import com.example.idealselect.entity.Ideal;
import com.example.idealselect.entity.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;


@Mapper
public interface ReplyMapper {
    void save(Reply reply);
    void update(Long id, Reply reply);
    Optional<Reply> findById(Long id);
    List<Reply> findAllBySelectionId(Long selectionId);
    List<Reply> findLimitListBySelectionId(Long selectionId, int startNum);

    void deleteById(Long id);
}
