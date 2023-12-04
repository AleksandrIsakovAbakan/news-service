package com.example.news.entity.v1;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comments")
public class CommentNewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String text;

    @Column(name = "comment_time", columnDefinition = "DATE NOT NULL")
    private LocalDateTime commentTime;

    @Column(name = "news_id", columnDefinition = "VARCHAR(255) NOT NULL")
    private int newsId;

    @Column(name = "user_id", columnDefinition = "VARCHAR(255) NOT NULL")
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="news_id", referencedColumnName = "id", insertable = false, updatable = false)
    private NewsEntity newsEntity;

    public void setNewsEntity(NewsEntity newsEntity) {
        this.newsEntity = newsEntity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCommentTime(LocalDateTime commentTime) {
        this.commentTime = commentTime;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
