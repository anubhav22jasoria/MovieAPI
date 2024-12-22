package com.anubhav.movieApi.controllers;

import com.anubhav.movieApi.dto.MovieDto;
import com.anubhav.movieApi.entities.Movie;
import com.anubhav.movieApi.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovie(@RequestPart MultipartFile multipartFile,
                                             @RequestPart String movieDto) throws IOException {
        MovieDto movieDtoObj = stringToMovieDto(movieDto);
        MovieDto savedMovie = movieService.addMovie(movieDtoObj, multipartFile);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }



    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Integer movieId){
        MovieDto movieDto = movieService.getMovie(movieId);
        return new ResponseEntity<>(movieDto,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMovies(){
        List<MovieDto> movieDto = movieService.getAllMovies();
        return new ResponseEntity<>(movieDto,HttpStatus.OK);
    }


    private MovieDto stringToMovieDto(String movieDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDto, MovieDto.class);
    }
}
