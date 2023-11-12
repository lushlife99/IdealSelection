package com.example.idealselect.repository;

import com.example.idealselect.entity.IdealSelection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface IdealSelectionMapper {

    void save(IdealSelection idealSelection);
    void update(Long id, IdealSelection selection);
    Optional<IdealSelection> findById(Long id);
    Optional<IdealSelection> findByIdAllResult(Long id);
    void deleteById(Long id);
    List<IdealSelection> findAll();
    List<IdealSelection> findByCreatorId(Long creatorId);
    List<IdealSelection> findPageableByCreatorId(Long creatorId, int pageNum);
    List<IdealSelection> findAllBySearchCond(SelectionSearchCond searchCond, int pageNum);

}