package com.anubhav.movieApi.service;

import com.anubhav.movieApi.dto.MovieDto;
import com.anubhav.movieApi.entities.Movie;
import com.anubhav.movieApi.exception.MovieNotFoundException;
import com.anubhav.movieApi.repository.MovieRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private static Logger logger = LogManager.getLogger(MovieServiceImpl.class);
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
        String uploadFileName = fileService.fileUpload(path, multipartFile);
        String posterUrl = generatePosterUrl(uploadFileName);

        Movie movie = toMovieEntity(movieDto);
        movie.setPoster(uploadFileName);

        Movie savedMovie = movieRepository.save(movie);
        logger.info("Movie added with movieID : "+savedMovie.getMovieId());
        return toMovieDto(savedMovie, posterUrl);
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie ID doesn't exist"));
        String posterUrl = generatePosterUrl(movie.getPoster());
        return toMovieDto(movie, posterUrl);
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movie -> toMovieDto(movie, generatePosterUrl(movie.getPoster())))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile multipartFile) throws IOException {
        Movie existingMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie doesn't exist with the ID: " + movieId));

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String oldPoster = existingMovie.getPoster();
            Files.deleteIfExists(Paths.get(path + File.separator + oldPoster));

            String newPoster = fileService.fileUpload(path, multipartFile);
            existingMovie.setPoster(newPoster);
        }

        updateMovieEntity(existingMovie, movieDto);

        Movie savedMovie = movieRepository.save(existingMovie);
        String posterUrl = generatePosterUrl(existingMovie.getPoster());
        logger.info("Movie added with movieID : "+savedMovie.getMovieId()+ " is updated");
        return toMovieDto(savedMovie, posterUrl);
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie with ID: " + movieId + " does not exist."));

        String oldPoster = movie.getPoster();
        Files.deleteIfExists(Paths.get(path + File.separator + oldPoster));

        movieRepository.deleteById(movieId);
        logger.info("Movie added with movieID : "+movie.getMovieId()+ " is deleted");
        return "Movie with ID : "+movieId+" has been deleted successfully";
    }

    private void updateMovieEntity(Movie movie, MovieDto movieDto) {
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setMovieCast(movieDto.getMovieCast());
        movie.setTitle(movieDto.getTitle());
        movie.setDirector(movieDto.getDirector());
        movie.setStudio(movieDto.getStudio());
    }

    private Movie toMovieEntity(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setMovieCast(movieDto.getMovieCast());
        movie.setTitle(movieDto.getTitle());
        movie.setDirector(movieDto.getDirector());
        movie.setStudio(movieDto.getStudio());
        return movie;
    }

    private MovieDto toMovieDto(Movie movie, String posterUrl) {
        return new MovieDto(
                movie.getMovieId(),
                movie.getReleaseYear(),
                movie.getMovieCast(),
                movie.getPoster(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                posterUrl
        );
    }

    private String generatePosterUrl(String fileName) {
        return fileUrl + "/file/" + fileName;
    }
}
