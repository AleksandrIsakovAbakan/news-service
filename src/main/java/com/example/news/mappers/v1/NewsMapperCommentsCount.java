package com.example.news.mappers.v1;

import com.example.news.api.v1.request.NewsRq;
import com.example.news.api.v1.response.NewsRsCommentsCount;
import com.example.news.entity.v1.CommentNewsEntity;
import com.example.news.entity.v1.NewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface NewsMapperCommentsCount {

    NewsMapperCommentsCount INSTANCE = Mappers.getMapper(NewsMapperCommentsCount.class);

    List<NewsRsCommentsCount> toDTO(List<NewsEntity> list);

    @Mapping(target = "commentListCount", source = "commentNewsEntityList", qualifiedByName = "listToCount")
    NewsRsCommentsCount toDTO(NewsEntity newsEntity);
    @Mapping(target = "newsCategoryEntity", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    NewsEntity toModel(NewsRq newsRq);

    @Named("listToCount")
    static int listToCount(List<CommentNewsEntity> commentNewsEntityList) {
        return commentNewsEntityList.size();
    }
}
