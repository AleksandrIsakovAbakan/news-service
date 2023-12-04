package com.example.news.controllers.v1;

import com.example.news.api.v1.request.NewsCategoryRq;
import com.example.news.api.v1.response.NewsCategoryRs;
import com.example.news.service.v1.NewsCategoryService;
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
@RequestMapping(path = "/v1/newsCategory", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsCategoryController {

    private final NewsCategoryService newsCategoryService;

    @GetMapping
    public ResponseEntity<List<NewsCategoryRs>> getPageNewsCategory(@RequestParam @NotEmpty String userName,
                                                                    @RequestParam @NotEmpty String password,
                                                                    @RequestParam(required = false) Integer offset,
                                                                    @RequestParam(required = false) Integer perPage)
    {
        return new ResponseEntity<>(newsCategoryService.getAllNewsCategory(userName, password, offset, perPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCategoryRs> getNewsCategoryId(@PathVariable @Min(0) Long id,
                                            @RequestParam @NotEmpty String userName,
                                            @RequestParam @NotEmpty String password)
    {
        return new ResponseEntity<>(newsCategoryService.getIdNewsCategory(id, userName, password), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<NewsCategoryRs> editNewsCategory(@RequestParam @NotEmpty String userName,
                                                           @RequestParam @NotEmpty String password,
                                                           @Validated @RequestBody NewsCategoryRq newsCategoryRq)
    {
        return new ResponseEntity<>(newsCategoryService.putIdNewsCategory(userName, password, newsCategoryRq), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<NewsCategoryRs> addNewsCategory(@RequestParam @NotEmpty String userName,
                                          @RequestParam @NotEmpty String password,
                                          @Validated @RequestBody NewsCategoryRq newsCategoryRq)
    {
        return new ResponseEntity<>(newsCategoryService.addNewsCategory(userName, password, newsCategoryRq), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NewsCategoryRs> deleteNewsCategory(@PathVariable @Min(0) Long id,
                                             @RequestParam @NotEmpty String userName,
                                             @RequestParam @NotEmpty String password)
    {
        newsCategoryService.deleteNewsCategory(id, userName, password);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
