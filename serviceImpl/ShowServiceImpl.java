package serviceImpl;

import service.ShowService;
import dao.ShowDAO;
import daoImpl.ShowDAOImpl;
import models.Show;
import exception.InvalidMovieException;

import java.time.LocalDateTime;
import java.util.List;

public class ShowServiceImpl implements ShowService {

    private ShowDAO showDAO = new ShowDAOImpl();

    @Override
    public List<Show> getAllShows() {
        return showDAO.getAllShows();
    }

    @Override
    public Show getShowById(int showId) throws InvalidMovieException {
        Show s = showDAO.getShowById(showId);
        if (s == null) throw new InvalidMovieException("Show not found: " + showId);
        return s;
    }

    @Override
    public void addShow(Show show) {
        showDAO.addShow(show);
    }

    @Override
    public void updateShow(Show show) throws InvalidMovieException {
        Show s = showDAO.getShowById(show.getShowId());
        if (s == null) throw new InvalidMovieException("Cannot update - show not found: " + show.getShowId());
        showDAO.updateShow(show);
    }

    @Override
    public void deleteShow(int showId) throws InvalidMovieException {
        Show s = showDAO.getShowById(showId);
        if (s == null) throw new InvalidMovieException("Cannot delete - show not found: " + showId);
        showDAO.deleteShow(showId);
    }

    @Override
    public List<Show> getShowsByMovie(int movielId) throws InvalidMovieException {
        return showDAO.getShowsByMovie(movielId);
    }

    @Override
    public List<Show> getShowsByTime(LocalDateTime time) {
        return showDAO.getShowsByTime(time);
    }
}
