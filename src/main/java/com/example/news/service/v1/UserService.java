package com.example.news.service.v1;

import com.example.news.api.v1.request.CommentRq;
import com.example.news.api.v1.request.UserRq;
import com.example.news.api.v1.response.UserRS;
import com.example.news.entity.v1.CommentNewsEntity;
import com.example.news.entity.v1.NewsEntity;
import com.example.news.entity.v1.UserEntity;
import com.example.news.exception.v1.AccessDeniedException;
import com.example.news.exception.v1.AlreadySuchNameException;
import com.example.news.exception.v1.EntityNotFoundException;
import com.example.news.mappers.v1.UserMapper;
import com.example.news.repository.v1.CommentNewsRepository;
import com.example.news.repository.v1.NewsRepository;
import com.example.news.repository.v1.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final NewsRepository newsRepository;

    private final CommentNewsRepository commentNewsRepository;

    static final String ACCESS_DENIED = "Access denied";
    private static final String USER_IS_NOT_FOUND_ID = "User is not found Id=";
    private static final String ACCESS_DENIED_EXCEPTION = "AccessDeniedException: ";

    @Value("${app.admin.login}")
    private String nameAdmin;

    @Value("${app.admin.password}")
    private String passwordAdmin;

    public void testAccess(String userName, String password){
        if (userName != null && password != null) {
            if (!userName.equals(nameAdmin) || !password.equals(passwordAdmin)) {
                log.info(ACCESS_DENIED_EXCEPTION + userName + ", " + password);
                throw new AccessDeniedException(ACCESS_DENIED);
            }
        } else {
            log.info(ACCESS_DENIED_EXCEPTION + userName + ", " + password);
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    public List<UserRS> getAllUsers(String userName, String password, Integer offset, Integer perPage) {

        testAccess(userName, password);

        if (offset == null) offset = 0;
        if (perPage == null) perPage = 10;
        Pageable pageable = PageRequest.of(offset, perPage);

        List<UserEntity> content = userRepository.findAll(pageable).getContent();
        log.info("getAllUsers: " + userName + ", " + password + ", " + offset + ", " + perPage);

        return UserMapper.INSTANCE.toDTO(content);
    }

    public UserRS getIdUser(Long id, String userName, String password) {

        testAccess(userName, password);

        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            log.info("getIdUser: " + userName + ", " + password + ", " + id);
            return UserMapper.INSTANCE.toDTO(byId.get());
        }
        log.info("EntityNotFoundException id=" + id);
        throw new EntityNotFoundException(USER_IS_NOT_FOUND_ID + id);
    }

    public UserRS addUser(String userName, String password, UserRq userRQ) {

        testAccess(userName, password);

        userRQ.setRegTime(LocalDateTime.now());
        UserEntity user = UserMapper.INSTANCE.toModel(userRQ);
        user.setId(0);
        Optional<UserEntity> byName = userRepository.findByName(userRQ.getName());
        if (byName.isPresent()){
            throw new AlreadySuchNameException("There is already such a name " + userRQ.getName());
        }
        UserEntity userEntity = userRepository.save(user);
        log.info("addUser: " + userName + ", " + password + ", " + userRQ);

        return UserMapper.INSTANCE.toDTO(userEntity);
    }

    public void deleteUser(Long id, String userName, String password) {

        testAccess(userName, password);

        Optional<UserEntity> byId = userRepository.findById(id);

        if (byId.isEmpty()) {
            throw new EntityNotFoundException(USER_IS_NOT_FOUND_ID + id);
        }

        userRepository.deleteById(id);
        log.info("deleteUser: " + userName + ", " + password + ", " + id);
    }

    public UserRS putIdUser(String userName, String password, UserRq userRQ) {

        testAccess(userName, password);

        Optional<UserEntity> byId = userRepository.findById(userRQ.getId());

        if (byId.isEmpty()) {
            throw new EntityNotFoundException(USER_IS_NOT_FOUND_ID + userRQ.getId());
        }

        if (userRQ.getRegTime() == null) {
            userRQ.setRegTime(LocalDateTime.now());
        }
        UserEntity user = UserMapper.INSTANCE.toModel(userRQ);
        UserEntity save = userRepository.save(user);
        log.info("putIdUser: " + userName + ", " + password + ", " + userRQ);

        return UserMapper.INSTANCE.toDTO(save);
    }

    public void testAccessUser(String userName, String password, long id, String newsOrComment) {

        Optional<UserEntity> byName = userRepository.findByName(userName);
        if (byName.isEmpty()){
            throw new EntityNotFoundException("User is not found userName=" + userName);
        }
        if (newsOrComment.equals("news")) {
            Optional<NewsEntity> byNews = newsRepository.findById(id);
            if (byNews.isEmpty()) {
                throw new EntityNotFoundException("News is not found newsId=" + id);
            }
            if (byName.get().getId() != byNews.get().getUserId()){
                throw new AccessDeniedException(ACCESS_DENIED);
            }
        } else {
            Optional<CommentNewsEntity> byComment = commentNewsRepository.findById(id);
            if (byComment.isEmpty()) {
                throw new EntityNotFoundException("Comment is not found commentId=" + id);
            }
            if (byName.get().getId() != byComment.get().getUserId()){
                throw new AccessDeniedException(ACCESS_DENIED);
            }
        }
        if (!userName.equals(byName.get().getName()) || !password.equals(byName.get().getPassword())){
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    public boolean getIdUserNews(Long id) {

        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            log.info("EntityNotFoundException id=" + id);
            throw new EntityNotFoundException(USER_IS_NOT_FOUND_ID + id);
        }
        log.info("getIdUserNews: " + id);
        return true;
    }

    public void testAccessComment(String userName, String password, CommentRq commentRq) {
        if (userName != null && password != null) {
            Optional<UserEntity> byName = userRepository.findByName(userName);
            if (byName.isEmpty()){
                log.info(ACCESS_DENIED_EXCEPTION + userName + ", " + password);
                throw new AccessDeniedException(ACCESS_DENIED);
            } else {
                if (byName.get().getId() != commentRq.getUserId()){
                    throw new AccessDeniedException(ACCESS_DENIED + commentRq.getUserId());
                }
            }
        } else {
            log.info(ACCESS_DENIED_EXCEPTION + userName + ", " + password);
            throw new AccessDeniedException(ACCESS_DENIED + userName + ", " + password);
        }
    }
}
