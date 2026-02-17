package database;

import models.*;
import enums.Genre;
import enums.SeatType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InMemoryDatabase {

    private static List<Movie> movies = new ArrayList<>();
    private static List<Show> shows = new ArrayList<>();

    static {
        // Create Movies
        Movie m1 = new Movie(1, "Pathaan", Genre.ACTION, 160, 250.0, null);
        Movie m2 = new Movie(2, "RRR", Genre.ACTION, 185, 300.0, null);
        Movie m3 = new Movie(3, "Jawan", Genre.ACTION, 170, 280.0, null);
        Movie m4 = new Movie(4, "Gadar 2", Genre.ACTION, 155, 230.0, null);
        Movie m5 = new Movie(5, "3 Idiots", Genre.COMEDY, 165, 200.0, null);
        Movie m6 = new Movie(6, "Dangal", Genre.SPORT, 161, 240.0, null);
        Movie m7 = new Movie(7, "Chhichhore", Genre.DRAMA, 140, 210.0, null);
        Movie m8 = new Movie(8, "Brahmastra", Genre.FANTASY, 165, 260.0, null);
        Movie m9 = new Movie(9, "Uri: The Surgical Strike", Genre.THRILLER, 138, 220.0, null);
        Movie m10 = new Movie(10, "Shershaah", Genre.BIOPIC, 145, 230.0, null);

        movies.add(m1); movies.add(m2); movies.add(m3); movies.add(m4); movies.add(m5);
        movies.add(m6); movies.add(m7); movies.add(m8); movies.add(m9); movies.add(m10);

        // Create Shows with default 30 seats and some seat types/prices
        int totalSeats = 30;
        int id = 1;
        // for simplicity make 3 shows per movie
        for (Movie m : movies) {
            List<Show> movieShows = new ArrayList<>();
            for (int s = 0; s < 3; s++) {
                LocalDateTime time = LocalDateTime.of(2025, 11, 13, 9 + s * 3 + (id % 3), 0);
                Show show = new Show(id, null, (s % 3) + 1, time, new ArrayList<>(), totalSeats);
                // set seat types/pricing
                for (int seatNo = 1; seatNo <= totalSeats; seatNo++) {
                    SeatType type = SeatType.REGULAR;
                    double price = m.getBasePrice();
                    if (seatNo <= 4) { type = SeatType.VIP; price = price * 1.5; }
                    else if (seatNo <= 10) { type = SeatType.PREMIUM; price = price * 1.2; }
                    else { type = SeatType.REGULAR; price = price; }
                    show.getSeats().set(seatNo - 1, new Seat(seatNo, type, price, false));
                }
                movieShows.add(show);
                shows.add(show);
                id++;
            }
            m.setShows(movieShows);
            // link movie in show's objects
            for (Show sh : movieShows) sh.setMovie(m);
        }
    }

    public static List<Movie> getMovies() {
        return movies;
    }

    public static List<Show> getShows() {
        return shows;
    }
}
