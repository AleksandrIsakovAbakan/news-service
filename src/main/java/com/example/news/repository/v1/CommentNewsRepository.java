package com.example.news.repository.v1;

import com.example.news.entity.v1.CommentNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentNewsRepository extends JpaRepository<CommentNewsEntity, Long> {
    List<CommentNewsEntity> findAllByNewsId(long id);
}
