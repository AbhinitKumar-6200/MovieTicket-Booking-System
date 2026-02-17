package daoImpl;

import dao.MovieDAO;
import database.InMemoryDatabase;
import models.Movie;

import java.util.List;
import java.util.Optional;

public class MovieDAOImpl implements MovieDAO {

    @Override
    public List<Movie> getAllMovies() {
        return InMemoryDatabase.getMovies();
    }

    @Override
    public Movie getMovieById(int movieId) {
        Optional<Movie> opt = getAllMovies().stream().filter(m -> m.getMovielId() == movieId).findFirst();
        return opt.orElse(null);
    }

    @Override
    public void addMovie(Movie movie) {
        getAllMovies().add(movie);
    }

    @Override
    public void updateMovie(Movie movie) {
        Movie existing = getMovieById(movie.getMovielId());
        if (existing != null) {
            existing.setTitle(movie.getTitle());
            existing.setGenre(movie.getGenre());
            existing.setDuration(movie.getDuration());
            existing.setBasePrice(movie.getBasePrice());
            existing.setShows(movie.getShows());
        }
    }

    @Override
    public void deleteMovie(int movielId) {
        getAllMovies().removeIf(m -> m.getMovielId() == movielId);
    }
}
