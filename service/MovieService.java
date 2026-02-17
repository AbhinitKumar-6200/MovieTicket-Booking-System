package service;

import models.Movie;
import enums.Genre;
import exception.InvalidMovieException;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(int movielId) throws InvalidMovieException;
    void addMovie(Movie movie);
    void updateMovie(Movie movie) throws InvalidMovieException;
    void deleteMovie(int movielId) throws InvalidMovieException;
    List<Movie> getMoviesByGenre(Genre genre);
}
