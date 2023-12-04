package com.example.news.service.v1;

import com.example.news.api.v1.request.NewsCategoryRq;
import com.example.news.api.v1.response.NewsCategoryRs;
import com.example.news.entity.v1.NewsCategoryEntity;
import com.example.news.exception.v1.AlreadySuchNameException;
import com.example.news.exception.v1.EntityNotFoundException;
import com.example.news.mappers.v1.NewsCategoryMapper;
import com.example.news.repository.v1.NewsCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;

    private final UserService userService;

    public List<NewsCategoryRs> getAllNewsCategory(String userName, String password, Integer offset, Integer perPage) {

        userService.testAccess(userName, password);
        if (offset == null) offset = 0;
        if (perPage == null) perPage = 10;

        Pageable pageable = PageRequest.of(offset, perPage);

        List<NewsCategoryEntity> content = newsCategoryRepository.findAll(pageable).getContent();
        log.info("getAllNewsCategory: " + userName + ", " + password + ", " + offset + ", " + perPage);

        return NewsCategoryMapper.INSTANCE.toDTO(content);
    }

    public NewsCategoryRs getIdNewsCategory(Long id, String userName, String password) {

        userService.testAccess(userName, password);

        Optional<NewsCategoryEntity> byId = newsCategoryRepository.findById(id);

        if (byId.isPresent()) {
            log.info("getIdNewsCategory: " + userName + ", " + password + ", " + id);
            return NewsCategoryMapper.INSTANCE.toDTO(byId.get());
        }

        throw new EntityNotFoundException("News category is not found Id=" + id);
    }

    public NewsCategoryRs putIdNewsCategory(String userName, String password, NewsCategoryRq newsCategoryRq) {

        userService.testAccess(userName, password);

        Optional<NewsCategoryEntity> byId = newsCategoryRepository.findById(newsCategoryRq.getId());

        if (byId.isEmpty()) {
            throw new EntityNotFoundException("News category is not found Id=" + newsCategoryRq.getId());
        }

        NewsCategoryEntity newsCategoryEntity = NewsCategoryMapper.INSTANCE.toModel(newsCategoryRq);
        NewsCategoryEntity save = newsCategoryRepository.save(newsCategoryEntity);
        log.info("putIdNewsCategory: " + userName + ", " + password + ", " + newsCategoryRq);

        return NewsCategoryMapper.INSTANCE.toDTO(save);
    }

    public NewsCategoryRs addNewsCategory(String userName, String password, NewsCategoryRq newsCategoryRq) {

        userService.testAccess(userName, password);

        NewsCategoryEntity newsCategoryEntity = NewsCategoryMapper.INSTANCE.toModel(newsCategoryRq);
        Optional<NewsCategoryEntity> newsCategoryEntity1 = newsCategoryRepository
                .findByNewsCategory(newsCategoryRq.getNewsCategory());
        if (newsCategoryEntity1.isPresent()){
            throw new AlreadySuchNameException("There is already such a news category " + newsCategoryRq.getNewsCategory());
        }

        NewsCategoryEntity save = newsCategoryRepository.save(newsCategoryEntity);
        log.info("addNewsCategory: " + userName + ", " + password + ", " + newsCategoryRq);

        return NewsCategoryMapper.INSTANCE.toDTO(save);
    }

    public void deleteNewsCategory(Long id, String userName, String password) {

        userService.testAccess(userName, password);

        newsCategoryRepository.deleteById(id);
        log.info("deleteNewsCategory: " + userName + ", " + password + ", " + id);
    }
}
