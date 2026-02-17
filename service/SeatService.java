package service;

import models.Seat;
import exception.InvalidMovieException;
import exception.SeatNotAvailableException;

import java.util.List;

public interface SeatService {
    List<Seat> getSeatsByShow(int showId) throws InvalidMovieException;
    List<Seat> getAvailableSeats(int showId) throws InvalidMovieException;
    void bookSeat(int showId, int seatNumber) throws SeatNotAvailableException, InvalidMovieException;
    void cancelSeat(int showId, int seatNumber) throws InvalidMovieException;
    double calculateSeatPrice(int showId, int seatNumber) throws InvalidMovieException;
}
