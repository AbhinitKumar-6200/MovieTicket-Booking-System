package serviceImpl;

import service.MovieService;
import dao.MovieDAO;
import daoImpl.MovieDAOImpl;
import models.Movie;
import enums.Genre;
import exception.InvalidMovieException;

import java.util.List;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {

    private MovieDAO movieDAO = new MovieDAOImpl();

    @Override
    public List<Movie> getAllMovies() {
        return movieDAO.getAllMovies();
    }

    @Override
    public Movie getMovieById(int movielId) throws InvalidMovieException {
        Movie m = movieDAO.getMovieById(movielId);
        if (m == null) throw new InvalidMovieException("Movie not found with id: " + movielId);
        return m;
    }

    @Override
    public void addMovie(Movie movie) {
        movieDAO.addMovie(movie);
    }

    @Override
    public void updateMovie(Movie movie) throws InvalidMovieException {
        Movie ex = movieDAO.getMovieById(movie.getMovielId());
        if (ex == null) throw new InvalidMovieException("Cannot update - movie not found: " + movie.getMovielId());
        movieDAO.updateMovie(movie);
    }

    @Override
    public void deleteMovie(int movielId) throws InvalidMovieException {
        Movie ex = movieDAO.getMovieById(movielId);
        if (ex == null) throw new InvalidMovieException("Cannot delete - movie not found: " + movielId);
        movieDAO.deleteMovie(movielId);
    }

    @Override
    public List<Movie> getMoviesByGenre(Genre genre) {
        return movieDAO.getAllMovies().stream().filter(m -> m.getGenre() == genre).collect(Collectors.toList());
    }
}
