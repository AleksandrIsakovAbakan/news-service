package com.example.news.controllers.v1;

import com.example.news.api.v1.request.NewsRq;
import com.example.news.api.v1.response.NewsRsCommentsCount;
import com.example.news.api.v1.response.NewsRsCommentsList;
import com.example.news.service.v1.NewsService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsRsCommentsCount>> getPageNews(@RequestParam(required = false) Integer offset,
                                                                 @RequestParam(required = false) Integer perPage)
    {
        return new ResponseEntity<>(newsService.getAllNews(offset, perPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsRsCommentsList> getNewsId(@PathVariable @Min(0) Long id)
    {
        return new ResponseEntity<>(newsService.getIdNews(id), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<NewsRsCommentsList> editNews(@RequestParam @NotEmpty String userName,
                                                       @RequestParam @NotEmpty String password,
                                                       @Validated @RequestBody(required = false) NewsRq newsRq)
    {
        return new ResponseEntity<>(newsService.putIdNews(userName, password, newsRq), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<NewsRsCommentsList> addNews(@RequestParam @NotEmpty String userName,
                                                      @RequestParam @NotEmpty String password,
                                                      @Validated @RequestBody NewsRq newsRq)
    {
        return new ResponseEntity<>(newsService.addNews(userName, password, newsRq), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NewsRsCommentsList> deleteNews(@PathVariable @Min(0) Long id,
                                                         @RequestParam @NotEmpty String userName,
                                                         @RequestParam @NotEmpty String password)
    {
        newsService.deleteNews(id, userName, password);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
