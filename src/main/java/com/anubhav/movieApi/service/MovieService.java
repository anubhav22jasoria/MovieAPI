package com.anubhav.movieApi.service;

import com.anubhav.movieApi.dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile multipartFile) throws IOException;
    MovieDto getMovie(Integer movieId);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Integer movieId,MovieDto movieDto, MultipartFile multipartFile) throws IOException;
    String deleteMovie(Integer movieId) throws IOException;
}
