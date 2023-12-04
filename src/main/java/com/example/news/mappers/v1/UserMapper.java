package com.example.news.mappers.v1;

import com.example.news.api.v1.request.UserRq;
import com.example.news.api.v1.response.UserRS;
import com.example.news.entity.v1.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserRS> toDTO(List<UserEntity> list);

    UserRS toDTO(UserEntity userEntity);

    UserEntity toModel(UserRq userRQ);

}
