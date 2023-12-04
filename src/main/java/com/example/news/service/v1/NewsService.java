package com.example.news.service.v1;

import com.example.news.api.v1.request.NewsRq;
import com.example.news.api.v1.response.NewsRsCommentsCount;
import com.example.news.api.v1.response.NewsRsCommentsList;
import com.example.news.aspects.v1.DeletingNews;
import com.example.news.aspects.v1.EditingNews;
import com.example.news.entity.v1.NewsCategoryEntity;
import com.example.news.entity.v1.NewsEntity;
import com.example.news.entity.v1.UserEntity;
import com.example.news.exception.v1.AccessDeniedException;
import com.example.news.exception.v1.EntityNotFoundException;
import com.example.news.mappers.v1.NewsMapperCommentsCount;
import com.example.news.mappers.v1.NewsMapperCommentsList;
import com.example.news.repository.v1.NewsCategoryRepository;
import com.example.news.repository.v1.NewsRepository;
import com.example.news.repository.v1.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.news.service.v1.UserService.ACCESS_DENIED;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;

    private final UserRepository userRepository;

    private final NewsCategoryRepository newsCategoryRepository;

    private final UserService userService;

    public List<NewsRsCommentsCount> getAllNews(Integer offset, Integer perPage) {
        List<NewsEntity> contentList = new ArrayList<>();
        if (offset == null) offset = 0;
        if (perPage == null) perPage = 10;

        Pageable pageable = PageRequest.of(offset, perPage);

        Page<NewsEntity> content = newsRepository.findAll(pageable);
        if (!content.isEmpty()) {
            contentList = content.getContent();
        }
        log.info("getAllNews: " + offset + ", " + perPage);

        return NewsMapperCommentsCount.INSTANCE.toDTO(contentList);
    }

    public NewsRsCommentsList getIdNews(Long id) {

        Optional<NewsEntity> byId = newsRepository.findById(id);

        if (byId.isPresent()) {
            log.info("getIdNews: " + id);
            return NewsMapperCommentsList.INSTANCE.toDTO(byId.get());
        }

        throw new EntityNotFoundException("News not found Id=" + id);
    }

    @EditingNews
    public NewsRsCommentsList putIdNews(String userName, String password, NewsRq newsRq) {

        userService.testAccessUser(userName, password, newsRq.getUserId(), "news");

        Optional<NewsEntity> byId = newsRepository.findById(newsRq.getId());

        if (byId.isEmpty()) {
            throw new EntityNotFoundException("News is not found Id=" + newsRq.getId());
        }

        NewsEntity newsEntity = NewsMapperCommentsList.INSTANCE.toModel(newsRq);
        NewsEntity save = newsRepository.save(newsEntity);
        log.info("putIdNews: " + userName + ", " + password + ", " + newsRq);

        return NewsMapperCommentsList.INSTANCE.toDTO(save);
    }

    public NewsRsCommentsList addNews(String userName, String password, NewsRq newsRq) {

        testingNewsRq(userName, password, newsRq);
        NewsEntity newsEntity = NewsMapperCommentsList.INSTANCE.toModel(newsRq);
        newsEntity.setId(0);
        NewsEntity save = newsRepository.save(newsEntity);
        log.info("addNews: " + userName + ", " + password + ", " + newsRq);

        return NewsMapperCommentsList.INSTANCE.toDTO(save);
    }

    @DeletingNews
    public void deleteNews(Long id, String userName, String password) {

        newsRepository.deleteById(id);
        log.info("deleteNews: " + userName + ", " + password + ", " + id);
    }

    public void testingNewsRq(String userName, String password, NewsRq newsRq){
        Optional<UserEntity> byName = userRepository.findByName(userName);
        if (byName.isEmpty()){
            throw new EntityNotFoundException("User is not found userName=" + userName);
        }
        if (byName.get().getId() != newsRq.getUserId()){
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        if (!userName.equals(byName.get().getName()) || !password.equals(byName.get().getPassword())){
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        if (newsRq.getNewsTime() == null) {
            newsRq.setNewsTime(LocalDateTime.now());
        }
        if (!userService.getIdUserNews((long) newsRq.getUserId())){
            log.info("EntityNotFoundException id=" + newsRq.getId());
            throw new EntityNotFoundException("User is not found Id=" + newsRq.getId());
        }
        Optional<NewsCategoryEntity> newsCategoryEntity = newsCategoryRepository.findById((long) newsRq.getCategoryId());
        if (newsCategoryEntity.isEmpty()){
            log.info("EntityNotFoundException news category=" + newsRq.getCategoryId());
            throw new EntityNotFoundException("News category is not found Id=" + newsRq.getCategoryId());
        }
    }
}
