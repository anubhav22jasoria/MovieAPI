package com.anubhav.movieApi.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    public Integer getMovieId() {
        return movieId;
    }

    public Movie() {
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Movie(Integer movieId, Integer releaseYear, Set<String> movieCast, String poster, String studio, String title, String director) {
        this.movieId = movieId;
        this.releaseYear = releaseYear;
        this.movieCast = movieCast;
        this.poster = poster;
        this.studio = studio;
        this.title = title;
        this.director = director;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
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

    @Column(nullable = false)
    @NotBlank(message = "--- Provide Movie's Director ---")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "--- Provide Movie's Title ---")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "--- Provide Movie's Studio ---")
    private String studio;

    @Column(nullable = false)
    @NotBlank(message = "--- Provide Movie's Poster ---")
    private String poster;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false)
    private Integer releaseYear;
}
