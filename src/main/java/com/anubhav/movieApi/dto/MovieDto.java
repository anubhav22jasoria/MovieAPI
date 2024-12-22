package com.anubhav.movieApi.dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Integer movieId;
    @NotBlank(message = "--- Provide Movie's Director ---")
    private String director;

    public MovieDto(Integer movieId, Integer releaseYear, Set<String> movieCast, String poster, String title, String director, String studio, String posterUrl) {
        this.movieId = movieId;
        this.releaseYear = releaseYear;
        this.movieCast = movieCast;
        this.poster = poster;
        this.title = title;
        this.director = director;
        this.studio = studio;
        this.posterUrl = posterUrl;
    }

    public MovieDto() {
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Set<String> getMovieCast() {
        return movieCast;
    }

    public void setMovieCast(Set<String> movieCast) {
        this.movieCast = movieCast;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @NotBlank(message = "--- Provide Movie's Title ---")
    private String title;
    @NotBlank(message = "--- Provide Movie's Studio ---")
    private String studio;
    @NotBlank(message = "--- Provide Movie's Poster ---")
    private String poster;


    @NotBlank(message = "--- Provide Movie's Poster Url ---")
    private String posterUrl;
    private Set<String> movieCast;
    @Column(nullable = false)
    private Integer releaseYear;
}
