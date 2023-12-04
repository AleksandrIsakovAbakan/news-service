package com.example.news.controllers.v1;

import com.example.news.api.v1.request.UserRq;
import com.example.news.api.v1.response.UserRS;
import com.example.news.service.v1.UserService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserRS>> getPageUsers(@RequestParam @NotNull String userName,
                                                     @RequestParam @NotNull String password,
                                                     @RequestParam(required = false) @Min(0) Integer offset,
                                                     @RequestParam(required = false) @Min(0) Integer perPage)
    {
        return new ResponseEntity<>(userService.getAllUsers(userName, password, offset, perPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRS> getUserId(@PathVariable(required = false) @Min(0) Long id,
                          @RequestParam @NotEmpty String userName,
                          @RequestParam @NotEmpty String password)
    {
        return new ResponseEntity<>(userService.getIdUser(id, userName, password), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<UserRS> editUser(@RequestParam @NotEmpty String userName,
                           @RequestParam @NotEmpty String password,
                           @Validated @RequestBody UserRq userRQ)
    {
        return new ResponseEntity<>(userService.putIdUser(userName, password, userRQ), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserRS> addUser(@RequestParam @NotEmpty String userName,
                          @RequestParam @NotEmpty String password,
                          @Validated @RequestBody(required = false) UserRq userRQ)
    {
        return new ResponseEntity<>(userService.addUser(userName, password, userRQ), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserRS> deleteUser(@PathVariable @Min(0) Long id,
                             @RequestParam @NotEmpty String userName,
                             @RequestParam @NotEmpty String password)
    {
        userService.deleteUser(id, userName, password);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
