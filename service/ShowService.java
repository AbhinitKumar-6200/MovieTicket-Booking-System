package service;

import models.Show;
import exception.InvalidMovieException;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowService {
    List<Show> getAllShows();
    Show getShowById(int showId) throws InvalidMovieException;
    void addShow(Show show);
    void updateShow(Show show) throws InvalidMovieException;
    void deleteShow(int showId) throws InvalidMovieException;
    List<Show> getShowsByMovie(int movielId) throws InvalidMovieException;
    List<Show> getShowsByTime(LocalDateTime time);
}
