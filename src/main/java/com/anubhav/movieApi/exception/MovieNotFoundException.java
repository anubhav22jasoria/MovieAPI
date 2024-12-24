package com.anubhav.movieApi.exception;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(String s){
        super(s);
    }
}
