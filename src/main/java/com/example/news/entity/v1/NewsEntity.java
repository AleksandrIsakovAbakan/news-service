package com.example.news.entity.v1;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "news")
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String text;

    @Column(name = "news_time", columnDefinition = "DATE NOT NULL")
    private LocalDateTime newsTime;

    @Column(name = "category_id", columnDefinition = "VARCHAR(255) NOT NULL")
    private int categoryId;

    @Column(name = "user_id", columnDefinition = "VARCHAR(255) NOT NULL")
    private int userId;

    @OneToMany(mappedBy="newsEntity", fetch = FetchType.LAZY)
    private List<CommentNewsEntity> commentNewsEntityList = new ArrayList<>();

    public void setCommentNewsEntityList(List<CommentNewsEntity> commentNewsEntityList) {
        this.commentNewsEntityList = commentNewsEntityList;
    }

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false, insertable=false, updatable=false)
    private NewsCategoryEntity newsCategoryEntity;

    public void setNewsCategoryEntity(NewsCategoryEntity newsCategoryEntity) {
        this.newsCategoryEntity = newsCategoryEntity;
    }

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
    private UserEntity userEntity;

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNewsTime(LocalDateTime newsTime) {
        this.newsTime = newsTime;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
