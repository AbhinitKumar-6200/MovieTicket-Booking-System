package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Show {
    private int showId;
    private Movie movie;
    private int screenNo;
    private LocalDateTime showTime;
    private List<Seat> seats;
    private int availableSeats;

    public Show() {
        this.seats = new ArrayList<>();
    }

    public Show(int showId, Movie movie, int screenNo, LocalDateTime showTime, List<Seat> seats, int totalSeats) {
        this.showId = showId;
        this.movie = movie;
        this.screenNo = screenNo;
        this.showTime = showTime;
        this.seats = seats == null ? new ArrayList<>() : seats;
        this.availableSeats = totalSeats;
        // initialize seats if empty (default layout)
        if (this.seats.isEmpty()) {
            for (int i = 1; i <= totalSeats; i++) {
                Seat s = new Seat(i, null, 0.0, false);
                this.seats.add(s);
            }
        }
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getScreenNo() {
        return screenNo;
    }

    public void setScreenNo(int screenNo) {
        this.screenNo = screenNo;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
        this.availableSeats = (int) seats.stream().filter(s -> !s.isBooked()).count();
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public synchronized boolean bookSeatNumber(int seatNumber) {
        for (Seat s : seats) {
            if (s.getSeatNumber() == seatNumber) {
                if (!s.isBooked()) {
                    s.setBooked(true);
                    availableSeats = Math.max(0, availableSeats - 1);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public synchronized boolean cancelSeatNumber(int seatNumber) {
        for (Seat s : seats) {
            if (s.getSeatNumber() == seatNumber) {
                if (s.isBooked()) {
                    s.setBooked(false);
                    availableSeats = availableSeats + 1;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Show{id=%d, movie=%s, screen=%d, time=%s, availableSeats=%d}",
                showId,
                movie == null ? "N/A" : movie.getTitle(),
                screenNo,
                showTime,
                availableSeats);
    }
}
