
package dao;

import java.util.List;

import models.Seat;

public interface SeatDAO {
    List<Seat> getSeatsByShow(int showId);
    Seat getSeat(int showId, int seatNumber);
    void addSeat(int showId, Seat seat);
    void updateSeat(int showId, Seat seat);
    void deleteSeat(int showId, int seatNumber);
}


