package com.example.news.api.v1.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRq {

    private long id;

    @NotEmpty
    private String password;

    private LocalDateTime regTime;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

}
