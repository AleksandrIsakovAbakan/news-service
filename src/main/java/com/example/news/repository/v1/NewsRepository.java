package com.example.news.repository.v1;

import com.example.news.entity.v1.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsRepository extends JpaRepository<NewsEntity, Long>, JpaSpecificationExecutor<NewsEntity> {

}
