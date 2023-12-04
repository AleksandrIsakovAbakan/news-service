package com.example.news.mappers.v1;

import com.example.news.api.v1.request.CommentRq;
import com.example.news.api.v1.response.CommentRs;
import com.example.news.entity.v1.CommentNewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    List<CommentRs> toDTO(List<CommentNewsEntity> list);

    CommentRs toDTO(CommentNewsEntity commentNewsEntity);

    CommentNewsEntity toModel(CommentRq commentRq);
}
