package models;

import enums.Genre;
import java.util.List;
import java.util.ArrayList;

public class Movie {
    private int movielId;
    private String title;
    private Genre genre;
    private int duration; // minutes
    private double basePrice;
    private List<Show> shows;

    public Movie() {
        this.shows = new ArrayList<>();
    }

    public Movie(int movielId, String title, Genre genre, int duration, double basePrice, List<Show> shows) {
        this.movielId = movielId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.basePrice = basePrice;
        this.shows = shows == null ? new ArrayList<>() : shows;
    }

    public int getMovielId() {
        return movielId;
    }

    public void setMovielId(int movielId) {
        this.movielId = movielId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    @Override
    public String toString() {
        return String.format("Movie{id=%d, title='%s', genre=%s, duration=%dmin, basePrice=%.2f, shows=%d}",
                movielId, title, genre, duration, basePrice, shows == null ? 0 : shows.size());
    }
}
