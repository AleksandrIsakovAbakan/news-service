package com.example.news.repository.v1;

import com.example.news.entity.v1.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findByName(String userName);
}
