package daoImpl;

import dao.ShowDAO;
import database.InMemoryDatabase;
import models.Show;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ShowDAOImpl implements ShowDAO {
    @Override
    public List<Show> getAllShows() {
        return InMemoryDatabase.getShows();
    }

    @Override
    public Show getShowById(int showId) {
        return getAllShows().stream().filter(s -> s.getShowId() == showId).findFirst().orElse(null);
    }

    @Override
    public void addShow(Show show) {
        getAllShows().add(show);
    }

    @Override
    public void updateShow(Show show) {
        Show ex = getShowById(show.getShowId());
        if (ex != null) {
            ex.setMovie(show.getMovie());
            ex.setScreenNo(show.getScreenNo());
            ex.setShowTime(show.getShowTime());
            ex.setSeats(show.getSeats());
            ex.setAvailableSeats(show.getAvailableSeats());
        }
    }

    @Override
    public void deleteShow(int showId) {
        getAllShows().removeIf(s -> s.getShowId() == showId);
    }

    @Override
    public List<Show> getShowsByMovie(int movielId) {
        return getAllShows().stream().filter(s -> s.getMovie() != null && s.getMovie().getMovielId() == movielId).collect(Collectors.toList());
    }

    @Override
    public List<Show> getShowsByTime(LocalDateTime time) {
        return getAllShows().stream().filter(s -> s.getShowTime().toLocalDate().equals(time.toLocalDate())).collect(Collectors.toList());
    }
}
