package com.example.news.controllers.v1;

import com.example.news.api.v1.response.NewsRsCommentsCount;
import com.example.news.model.v1.NewsFilter;
import com.example.news.service.v1.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<NewsRsCommentsCount>> getPageNews(@RequestParam(required = false) String nameCategory,
                                                                 @RequestParam(required = false) Integer idCategory,
                                                                 @RequestParam(required = false) String nameAuthor,
                                                                 @RequestParam(required = false) Integer idAuthor,
                                                                 @RequestParam(required = false) Long idNews,
                                                                 @RequestParam(required = false) Integer offset,
                                                                 @RequestParam(required = false) Integer perPage)
    {
        return new ResponseEntity<>(searchService.filterByNews(new NewsFilter(nameCategory, idCategory, nameAuthor,
                idAuthor, offset, perPage, idNews)), HttpStatus.OK);
    }
}
