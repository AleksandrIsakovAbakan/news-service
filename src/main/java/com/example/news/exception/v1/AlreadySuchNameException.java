package com.example.news.exception.v1;

public class AlreadySuchNameException extends RuntimeException{

    public AlreadySuchNameException(String errorMessage) {
        super(errorMessage);
    }
}
