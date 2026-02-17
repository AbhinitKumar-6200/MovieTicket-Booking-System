package daoImpl;

import dao.BookingDAO;
import models.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookingDAOImpl implements BookingDAO {

    // thread-safe list
    private static List<Booking> bookings = new CopyOnWriteArrayList<>();

    @Override
    public Booking getBookingById(int bookingId) {
        return bookings.stream().filter(b -> b.getBookingId() == bookingId).findFirst().orElse(null);
    }

    @Override
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    @Override
    public void updateBooking(Booking booking) {
        Booking ex = getBookingById(booking.getBookingId());
        if (ex != null) {
            bookings.remove(ex);
            bookings.add(booking);
        }
    }

    @Override
    public void deleteBooking(int bookingId) {
        bookings.removeIf(b -> b.getBookingId() == bookingId);
    }

    @Override
    public List<Booking> getBookingsByUser(String userName) {
        return bookings.stream().filter(b -> b.getUserName().equalsIgnoreCase(userName)).collect(Collectors.toList());
    }

    @Override
    public List<Booking> getBookingsByShow(int showId) {
        return bookings.stream().filter(b -> b.getShow() != null && b.getShow().getShowId() == showId).collect(Collectors.toList());
    }
}
