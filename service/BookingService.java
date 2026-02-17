package service;

import models.Booking;
import models.Seat;
import exception.InvalidBookingException;
import exception.InvalidMovieException;
import exception.SeatNotAvailableException;

import java.util.List;

public interface BookingService {
    Booking createBooking(String userName, int showId, List<Integer> seatNumbers) throws SeatNotAvailableException, InvalidMovieException;
    void cancelBooking(int bookingId) throws InvalidBookingException;
    Booking getBookingById(int bookingId) throws InvalidBookingException;
    List<Booking> getBookingsByUser(String userName);
    List<Booking> getBookingsByShow(int showId) throws InvalidMovieException;
    double calculateTotalAmount(List<Seat> seats);
}
