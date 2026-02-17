package daoImpl;

import dao.SeatDAO;
import database.InMemoryDatabase;
import models.Seat;
import models.Show;

import java.util.List;
import java.util.Optional;

public class SeatDAOImpl implements SeatDAO {
    @Override
    public List<Seat> getSeatsByShow(int showId) {
        Show s = InMemoryDatabase.getShows().stream().filter(sh -> sh.getShowId() == showId).findFirst().orElse(null);
        return s == null ? null : s.getSeats();
    }

    @Override
    public Seat getSeat(int showId, int seatNumber) {
        List<Seat> seats = getSeatsByShow(showId);
        if (seats == null) return null;
        Optional<Seat> opt = seats.stream().filter(se -> se.getSeatNumber() == seatNumber).findFirst();
        return opt.orElse(null);
    }

    @Override
    public void addSeat(int showId, Seat seat) {
        Show s = InMemoryDatabase.getShows().stream().filter(sh -> sh.getShowId() == showId).findFirst().orElse(null);
        if (s != null) s.getSeats().add(seat);
    }

    @Override
    public void updateSeat(int showId, Seat seat) {
        Show s = InMemoryDatabase.getShows().stream().filter(sh -> sh.getShowId() == showId).findFirst().orElse(null);
        if (s != null) {
            s.getSeats().removeIf(se -> se.getSeatNumber() == seat.getSeatNumber());
            s.getSeats().add(seat);
        }
    }

    @Override
    public void deleteSeat(int showId, int seatNumber) {
        Show s = InMemoryDatabase.getShows().stream().filter(sh -> sh.getShowId() == showId).findFirst().orElse(null);
        if (s != null) s.getSeats().removeIf(se -> se.getSeatNumber() == seatNumber);
    }
}
