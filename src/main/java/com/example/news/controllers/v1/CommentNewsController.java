package com.example.news.controllers.v1;

import com.example.news.api.v1.request.CommentRq;
import com.example.news.api.v1.response.CommentRs;
import com.example.news.api.v1.response.NewsRsCommentsList;
import com.example.news.service.v1.CommentNewsService;
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
@RequestMapping(path = "/v1/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentNewsController {

    private final CommentNewsService commentNewsService;

    @GetMapping("/{id}")
    public ResponseEntity<List<CommentRs>> getCommentNewsId(@PathVariable Long id)
    {
        return new ResponseEntity<>(commentNewsService.getIdNewsComments(id), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<CommentRs> editCommentNews(@RequestParam @NotEmpty String userName,
                                           @RequestParam @NotEmpty String password,
                                           @Validated @RequestBody CommentRq commentRq)
    {
        return new ResponseEntity<>(commentNewsService.putIdComment(userName, password, commentRq), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CommentRs> addCommentNews(@RequestParam @NotEmpty String userName,
                                          @RequestParam @NotEmpty String password,
                                          @Validated @RequestBody CommentRq commentRq)
    {
        return new ResponseEntity<>(commentNewsService.addComment(userName, password, commentRq), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NewsRsCommentsList> deleteCommentNews(@PathVariable @Min(0) Long id,
                                                                @RequestParam @NotEmpty String userName,
                                                                @RequestParam @NotEmpty String password)
    {
        commentNewsService.deleteComment(id, userName, password);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
