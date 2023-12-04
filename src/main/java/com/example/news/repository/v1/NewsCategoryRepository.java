package com.example.news.repository.v1;

import com.example.news.entity.v1.NewsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsCategoryRepository extends JpaRepository<NewsCategoryEntity, Long> {
    Optional<NewsCategoryEntity> findByNewsCategory(String newsCategory);
}
