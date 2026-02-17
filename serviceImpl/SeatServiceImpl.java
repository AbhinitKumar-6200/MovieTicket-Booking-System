package serviceImpl;

import service.SeatService;
import dao.SeatDAO;
import daoImpl.SeatDAOImpl;
import dao.ShowDAO;
import daoImpl.ShowDAOImpl;
import models.Seat;
import models.Show;
import exception.InvalidMovieException;
import exception.SeatNotAvailableException;

import java.util.List;
import java.util.stream.Collectors;

public class SeatServiceImpl implements SeatService {

    private SeatDAO seatDAO = new SeatDAOImpl();
    private ShowDAO showDAO = new ShowDAOImpl();

    @Override
    public List<Seat> getSeatsByShow(int showId) throws InvalidMovieException {
        Show s = showDAO.getShowById(showId);
        if (s == null) throw new InvalidMovieException("Show not found: " + showId);
        return seatDAO.getSeatsByShow(showId);
    }

    @Override
    public List<Seat> getAvailableSeats(int showId) throws InvalidMovieException {
        List<Seat> all = getSeatsByShow(showId);
        return all.stream().filter(se -> !se.isBooked()).collect(Collectors.toList());
    }

    @Override
    public void bookSeat(int showId, int seatNumber) throws SeatNotAvailableException, InvalidMovieException {
        Show s = showDAO.getShowById(showId);
        if (s == null) throw new InvalidMovieException("Show not found: " + showId);

        synchronized (s) {
            Seat seat = seatDAO.getSeat(showId, seatNumber);
            if (seat == null) throw new SeatNotAvailableException("Seat number invalid: " + seatNumber);
            if (seat.isBooked()) throw new SeatNotAvailableException("Seat already booked: " + seatNumber);
            // mark booked
            seat.setBooked(true);
            s.setAvailableSeats(Math.max(0, s.getAvailableSeats() - 1));
            seatDAO.updateSeat(showId, seat);
        }
    }

    @Override
    public void cancelSeat(int showId, int seatNumber) throws InvalidMovieException {
        Show s = showDAO.getShowById(showId);
        if (s == null) throw new InvalidMovieException("Show not found: " + showId);

        synchronized (s) {
            Seat seat = seatDAO.getSeat(showId, seatNumber);
            if (seat != null && seat.isBooked()) {
                seat.setBooked(false);
                s.setAvailableSeats(s.getAvailableSeats() + 1);
                seatDAO.updateSeat(showId, seat);
            }
        }
    }

    @Override
    public double calculateSeatPrice(int showId, int seatNumber) throws InvalidMovieException {
        Seat seat = seatDAO.getSeat(showId, seatNumber);
        if (seat == null) throw new InvalidMovieException("Seat not found: " + seatNumber);
        return seat.getPrice();
    }
}
    