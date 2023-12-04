package com.example.news.api.v1.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserRS {

    private int id;

    private String password;

    private LocalDateTime regTime;

    private String name;

    private String email;
}
