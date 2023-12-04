package com.example.news.exception.v1;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
