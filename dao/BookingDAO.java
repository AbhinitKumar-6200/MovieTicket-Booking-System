package dao;

import java.util.List;

import models.Booking;

public interface BookingDAO {
    Booking getBookingById(int bookingId);
    void addBooking(Booking booking);
    void updateBooking(Booking booking);
    void deleteBooking(int bookingId);
    List<Booking> getBookingsByUser(String userName);
    List<Booking> getBookingsByShow(int showId);
}
