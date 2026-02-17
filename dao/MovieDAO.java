
package dao;

import models.Movie;
import java.util.List;

public interface MovieDAO {
    List<Movie> getAllMovies();
    Movie getMovieById(int movieId);
    void addMovie(Movie movie);
    void updateMovie(Movie movie);
    void deleteMovie(int movielId);
}

