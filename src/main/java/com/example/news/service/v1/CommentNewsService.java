package com.example.news.service.v1;

import com.example.news.api.v1.request.CommentRq;
import com.example.news.api.v1.response.CommentRs;
import com.example.news.aspects.v1.DeletingComment;
import com.example.news.aspects.v1.EditingComment;
import com.example.news.entity.v1.CommentNewsEntity;
import com.example.news.entity.v1.NewsEntity;
import com.example.news.exception.v1.EntityNotFoundException;
import com.example.news.mappers.v1.CommentMapper;
import com.example.news.repository.v1.CommentNewsRepository;
import com.example.news.repository.v1.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentNewsService {

    private final CommentNewsRepository commentNewsRepository;

    private final NewsRepository newsRepository;

    private final UserService userService;

    public List<CommentRs> getIdNewsComments(Long id) {

        Optional<NewsEntity> byId = newsRepository.findById(id);

        if (byId.isPresent()) {

            List<CommentNewsEntity> commentNewsEntities = commentNewsRepository.findAllByNewsId(byId.get().getId());
            if (commentNewsEntities == null) commentNewsEntities = new ArrayList<>();

            log.info("getIdNewsComments: " + id);
            return CommentMapper.INSTANCE.toDTO(commentNewsEntities);
        }

        throw new EntityNotFoundException("News not found Id=" + id);
    }
    @EditingComment
    public CommentRs putIdComment(String userName, String password, CommentRq commentRq) {

        Optional<CommentNewsEntity> byId = commentNewsRepository.findById(commentRq.getId());

        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Comment is not found Id=" + commentRq.getId());
        }

        CommentNewsEntity commentNewsEntity = CommentMapper.INSTANCE.toModel(commentRq);
        CommentNewsEntity save = commentNewsRepository.save(commentNewsEntity);
        log.info("putIdComment: " + userName + ", " + password + ", " + commentRq);

        return CommentMapper.INSTANCE.toDTO(save);
    }

    public CommentRs addComment(String userName, String password, CommentRq commentRq) {

        userService.testAccessComment(userName, password, commentRq);

        if (commentRq.getCommentTime() == null) {
            commentRq.setCommentTime(LocalDateTime.now());
        }

        CommentNewsEntity commentNewsEntity = CommentMapper.INSTANCE.toModel(commentRq);

        CommentNewsEntity save = commentNewsRepository.save(commentNewsEntity);
        log.info("addComment: " + userName + ", " + password + ", " + commentRq);

        return CommentMapper.INSTANCE.toDTO(save);
    }
    @DeletingComment
    public void deleteComment(Long id, String userName, String password) {

        commentNewsRepository.deleteById(id);
        log.info("deleteComment: " + userName + ", " + password + ", " + id);
    }
}
