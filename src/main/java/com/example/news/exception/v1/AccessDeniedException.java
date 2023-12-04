package com.example.news.exception.v1;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(String errorMessage){ super(errorMessage);}
}
