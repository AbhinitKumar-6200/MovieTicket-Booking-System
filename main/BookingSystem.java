
package main;

import models.Movie;
import models.Show;
import models.Seat;
import models.Booking;
import exception.InvalidMovieException;
import exception.SeatNotAvailableException;
import exception.InvalidBookingException;
import service.MovieService;
import service.ShowService;
import service.SeatService;
import service.BookingService;
import serviceImpl.MovieServiceImpl;
import serviceImpl.ShowServiceImpl;
import serviceImpl.SeatServiceImpl;
import serviceImpl.BookingServiceImpl;
import util.MenuPrinter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Arrays;

public class BookingSystem {

    private static MovieService movieService = new MovieServiceImpl();
    private static ShowService showService = new ShowServiceImpl();
    private static SeatService seatService = new SeatServiceImpl();
    private static BookingService bookingService = new BookingServiceImpl();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            MenuPrinter.printMainMenu();
            int opt;
            try {
                opt = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }
            switch (opt) {
                case 1:
                    listAllMovies();
                    System.out.println();
                    break;
                case 2:
                    searchMovieByTitle(sc);
                    System.out.println();
                    break;
                case 3:
                    listShowsForMovie(sc);
                    System.out.println();
                    break;
                case 4:
                    showSeatMap(sc);
                    System.out.println();
                    break;
                case 5:
                    bookSeats(sc);
                    System.out.println();
                    break;
                case 6:
                    cancelBooking(sc);
                    System.out.println();
                    break;
                case 7:
                    myBookings(sc);
                    System.out.println();
                    break;
                case 8:
                    running = false;
                    System.out.println("Goodbye!");
                    System.out.println();
                    break;
                default:
                    System.out.println("Please choose a valid option.");
                    System.out.println();
            }
        }
        sc.close();
    }

    private static void listAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        System.out.println("Movies:");
        for (Movie m : movies) {
            System.out.println(m);
            if (m.getShows() != null) {
                for (Show s : m.getShows()) {
                    System.out.println("  - " + s);
                }
            }
        }
    }

    private static void searchMovieByTitle(Scanner sc) {
        System.out.print("Enter title or part of title: ");
        String q = sc.nextLine().trim().toLowerCase();
        List<Movie> results = movieService.getAllMovies().stream()
                .filter(m -> m.getTitle().toLowerCase().contains(q))
                .collect(Collectors.toList());
        if (results.isEmpty()) System.out.println("No movies found.");
        else results.forEach(System.out::println);
    }

    private static void listShowsForMovie(Scanner sc) {
        System.out.print("Enter movie id: ");
        int id = Integer.parseInt(sc.nextLine());
        try {
            Movie m = movieService.getMovieById(id);
            System.out.println("Shows for: " + m.getTitle());
            if (m.getShows() != null && !m.getShows().isEmpty()) {
                m.getShows().forEach(s -> System.out.println("  " + s));
            } else {
                System.out.println("No shows found for this movie.");
            }
        } catch (InvalidMovieException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showSeatMap(Scanner sc) {
        System.out.print("Enter show id: ");
        int id = Integer.parseInt(sc.nextLine());
        try {
            List<Seat> seats = seatService.getSeatsByShow(id);
            if (seats == null) {
                System.out.println("Show not found.");
                return;
            }
            System.out.println("Seat map (seatNumber : booked ? )");
            for (Seat seat : seats) {
                System.out.printf("%2d:%s  ", seat.getSeatNumber(), seat.isBooked() ? "X" : "O");
                if (seat.getSeatNumber() % 10 == 0) System.out.println();
            }
            System.out.println();
        } catch (InvalidMovieException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void bookSeats(Scanner sc) {
        try {
            System.out.print("Enter your name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter show id: ");
            int showId = Integer.parseInt(sc.nextLine());
            System.out.print("Enter seat numbers separated by comma (e.g. 1,2,3): ");
            String line = sc.nextLine();
            List<Integer> seatNumbers = Arrays.stream(line.split(","))
                    .map(String::trim).filter(s -> !s.isEmpty())
                    .map(Integer::parseInt).collect(Collectors.toList());

            Booking booking = bookingService.createBooking(name, showId, seatNumbers);
            System.out.println("Booking successful: " + booking);
        } catch (SeatNotAvailableException | InvalidMovieException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        }
    }

    private static void cancelBooking(Scanner sc) {
        System.out.print("Enter booking id: ");
        try {
            int bid = Integer.parseInt(sc.nextLine());
            bookingService.cancelBooking(bid);
            System.out.println("Booking cancelled: " + bid);
        } catch (InvalidBookingException e) {
            System.out.println("Cancel failed: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private static void myBookings(Scanner sc) {
        System.out.print("Enter your name: ");
        String user = sc.nextLine().trim();
        List<Booking> list = bookingService.getBookingsByUser(user);
        if (list == null || list.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            list.forEach(System.out::println);
        }
    }
}
