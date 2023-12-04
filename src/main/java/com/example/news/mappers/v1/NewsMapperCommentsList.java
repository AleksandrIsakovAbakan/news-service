package com.example.news.mappers.v1;

import com.example.news.api.v1.request.NewsRq;
import com.example.news.api.v1.response.NewsRsCommentsList;
import com.example.news.entity.v1.NewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface NewsMapperCommentsList {

    NewsMapperCommentsList INSTANCE = Mappers.getMapper(NewsMapperCommentsList.class);

    List<NewsRsCommentsList> toDTO(List<NewsEntity> list);

    @Mapping(target = "commentList", source = "commentNewsEntityList")
    NewsRsCommentsList toDTO(NewsEntity newsEntity);

    @Mapping(target = "userEntity", ignore = true)
    @Mapping(target = "newsCategoryEntity", ignore = true)
    NewsEntity toModel(NewsRq newsRq);
}
