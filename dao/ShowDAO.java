


package dao;

import models.Show;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowDAO {
    List<Show> getAllShows();
    Show getShowById(int showId);
    void addShow(Show show);
    void updateShow(Show show);
    void deleteShow(int showId);
    List<Show> getShowsByMovie(int movielId);
    List<Show> getShowsByTime(LocalDateTime time);
}
