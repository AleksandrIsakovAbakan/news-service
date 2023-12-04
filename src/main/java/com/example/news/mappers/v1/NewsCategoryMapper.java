package com.example.news.mappers.v1;

import com.example.news.api.v1.request.NewsCategoryRq;
import com.example.news.api.v1.response.NewsCategoryRs;
import com.example.news.entity.v1.NewsCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface NewsCategoryMapper {

    NewsCategoryMapper INSTANCE = Mappers.getMapper(NewsCategoryMapper.class);

    List<NewsCategoryRs> toDTO(List<NewsCategoryEntity> list);

    NewsCategoryRs toDTO(NewsCategoryEntity newsCategoryEntity);

    NewsCategoryEntity toModel(NewsCategoryRq newsCategoryRq);
}
