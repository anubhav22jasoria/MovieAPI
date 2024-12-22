package com.anubhav.movieApi.service;

import com.anubhav.movieApi.dto.MovieDto;
import com.anubhav.movieApi.entities.Movie;
import com.anubhav.movieApi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Value("${project.posters}")
    private String path;

    @Value("${base.url}")
    private String fileUrl;

    private final MovieRepository movieRepository;
    private final FileService fileService;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Transactional
    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile multipartFile) throws IOException {
        // Upload the file
        String uploadFileName = fileService.fileUpload(path, multipartFile);

        // Generate the poster URL
        String posterUrl = fileUrl + "/file/" + uploadFileName;

        // Create Movie entity
        Movie movie = new Movie();
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setMovieCast(movieDto.getMovieCast());
        movie.setPoster(uploadFileName);
        movie.setTitle(movieDto.getTitle());
        movie.setDirector(movieDto.getDirector());
        movie.setStudio(movieDto.getStudio());

        // Save to database
        Movie savedMovie = movieRepository.save(movie);

        // Convert to DTO
        return new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getReleaseYear(),
                savedMovie.getMovieCast(),
                savedMovie.getPoster(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                posterUrl
        );
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("MovieId doesn't exist"));
        String posterUrl = fileUrl + "/file/" + movie.getPoster();
        MovieDto movieDto =new MovieDto(
                movie.getMovieId(),
                movie.getReleaseYear(),
                movie.getMovieCast(),
                movie.getPoster(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                posterUrl
        );

        return movieDto;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> allMovies = movieRepository.findAll();
        List<MovieDto> allMoviesDto = new ArrayList<>();
        for(int i=0;i < allMovies.size();i++){
            Movie movie = allMovies.get(i);
            String posterUrl = fileUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getReleaseYear(),
                    movie.getMovieCast(),
                    movie.getPoster(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    posterUrl
            );
            allMoviesDto.add(movieDto);
        }
        return allMoviesDto;

    }
}
