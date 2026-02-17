package serviceImpl;

import service.BookingService;
import dao.BookingDAO;
import daoImpl.BookingDAOImpl;
import dao.ShowDAO;
import daoImpl.ShowDAOImpl;
import dao.SeatDAO;
import daoImpl.SeatDAOImpl;
import models.Booking;
import models.Seat;
import models.Show;
import enums.BookingStatus;
import exception.InvalidBookingException;
import exception.InvalidMovieException;
import exception.SeatNotAvailableException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingServiceImpl implements BookingService {

    private BookingDAO bookingDAO = new BookingDAOImpl();
    private ShowDAO showDAO = new ShowDAOImpl();
    private SeatDAO seatDAO = new SeatDAOImpl();
    private AtomicInteger bookingIdGenerator = new AtomicInteger(1000);

    @Override
    public Booking createBooking(String userName, int showId, List<Integer> seatNumbers) throws SeatNotAvailableException, InvalidMovieException {
        Show show = showDAO.getShowById(showId);
        if (show == null) throw new InvalidMovieException("Show not found: " + showId);

        List<Seat> bookedSeats = new ArrayList<>();
        double total = 0.0;

        // lock on show to avoid double booking
        synchronized (show) {
            for (Integer seatNum : seatNumbers) {
                Seat seat = seatDAO.getSeat(showId, seatNum);
                if (seat == null) throw new SeatNotAvailableException("Invalid seat number: " + seatNum);
                if (seat.isBooked()) throw new SeatNotAvailableException("Seat already booked: " + seatNum);
            }

            // all seats are free -> book them
            for (Integer seatNum : seatNumbers) {
                Seat seat = seatDAO.getSeat(showId, seatNum);
                seat.setBooked(true);
                show.setAvailableSeats(Math.max(0, show.getAvailableSeats() - 1));
                seatDAO.updateSeat(showId, seat);
                bookedSeats.add(seat);
                total += seat.getPrice();
            }
        }

        Booking booking = new Booking();
        int id = bookingIdGenerator.incrementAndGet();
        booking.setBookingId(id);
        booking.setUserName(userName);
        booking.setShow(show);
        booking.setBookedSeats(bookedSeats);
        booking.setTotalAmount(total);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED);

        bookingDAO.addBooking(booking);
        return booking;
    }

    @Override
    public void cancelBooking(int bookingId) throws InvalidBookingException {
        Booking b = bookingDAO.getBookingById(bookingId);
        if (b == null) throw new InvalidBookingException("Booking not found: " + bookingId);
        if (b.getStatus() == BookingStatus.CANCELLED) throw new InvalidBookingException("Already cancelled: " + bookingId);

        // free seats
        Show show = b.getShow();
        synchronized (show) {
            for (Seat seat : b.getBookedSeats()) {
                Seat s = seatDAO.getSeat(show.getShowId(), seat.getSeatNumber());
                if (s != null && s.isBooked()) {
                    s.setBooked(false);
                    show.setAvailableSeats(show.getAvailableSeats() + 1);
                    seatDAO.updateSeat(show.getShowId(), s);
                }
            }
        }

        b.setStatus(BookingStatus.CANCELLED);
        bookingDAO.updateBooking(b);
    }

    @Override
    public Booking getBookingById(int bookingId) throws InvalidBookingException {
        Booking b = bookingDAO.getBookingById(bookingId);
        if (b == null) throw new InvalidBookingException("Booking not found: " + bookingId);
        return b;
    }

    @Override
    public List<Booking> getBookingsByUser(String userName) {
        return bookingDAO.getBookingsByUser(userName);
    }

    @Override
    public List<Booking> getBookingsByShow(int showId) throws InvalidMovieException {
        return bookingDAO.getBookingsByShow(showId);
    }

    @Override
    public double calculateTotalAmount(List<Seat> seats) {
        return seats.stream().mapToDouble(Seat::getPrice).sum();
    }
}
