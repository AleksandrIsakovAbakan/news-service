package com.example.news.aspects.v1;

import com.example.news.api.v1.request.CommentRq;
import com.example.news.api.v1.request.NewsRq;
import com.example.news.service.v1.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AspectEditingAndDeletingNewsAndComment {

    private final UserService userService;

    @Pointcut("@annotation(DeletingNews)")
    public void handleDeletingNews() {
    }

    @Pointcut("@annotation(EditingNews)")
    public void handleEditingNews() {
    }

    @Pointcut("@annotation(DeletingComment)")
    public void handleDeletingComment() {
    }

    @Pointcut("@annotation(EditingComment)")
    public void handleEditingComment() {
    }

    @Before("handleDeletingNews()")
    public void deletingNews(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        log.info("Aspect delete News: " + args[1] + ", " + args[2] + ", " + args[0]);
        userService.testAccessUser((String) args[1], (String) args[2], (Long) args[0], "news");
    }

    @Before("handleEditingNews()")
    public void editingNews(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        NewsRq newsRq = (NewsRq) args[2];
        log.info("Aspect editing News: " + args[0] + ", " + args[1] + ", " + newsRq.getUserId());
        userService.testAccessUser((String) args[0], (String) args[1], newsRq.getUserId(), "news");
    }

    @Before("handleDeletingComment()")
    public void deletingComment(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        log.info("Aspect deleting comment: " + args[1] + ", " + args[2] + ", " + (long) args[0]);
        userService.testAccessUser((String) args[1], (String) args[2], (long) args[0], "comment");
    }

    @Before("handleEditingComment()")
    public void editingComment(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        CommentRq commentRq = (CommentRq) args[0];
        log.info("Aspect editing comment: " + args[1] + ", " + args[2] + ", " + commentRq.getUserId());
        userService.testAccessUser((String) args[1], (String) args[2], commentRq.getUserId(), "comment");
    }
}
