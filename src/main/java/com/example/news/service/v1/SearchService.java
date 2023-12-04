package com.example.news.service.v1;

import com.example.news.api.v1.response.NewsRsCommentsCount;
import com.example.news.entity.v1.NewsEntity;
import com.example.news.mappers.v1.NewsMapperCommentsCount;
import com.example.news.model.v1.NewsFilter;
import com.example.news.repository.v1.NewsRepository;
import com.example.news.repository.v1.NewsSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final NewsRepository newsRepository;

    public List<NewsRsCommentsCount> filterByNews(NewsFilter newsFilter) {

        List<NewsEntity> content = newsRepository.findAll(NewsSpecification.withFilter(newsFilter),
        PageRequest.of(newsFilter.getOffset(), newsFilter.getPerPage())).getContent();
        if (content.isEmpty()) {
            log.info("getAllNews: " + newsFilter);
            return NewsMapperCommentsCount.INSTANCE.toDTO(new ArrayList<>());
        } else {
            log.info("getAllNews: " + newsFilter);
            return NewsMapperCommentsCount.INSTANCE.toDTO(content);
        }
    }
}
