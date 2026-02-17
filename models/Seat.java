package models;

import enums.SeatType;

public class Seat {
    private int seatNumber;
    private SeatType seatType;
    private double price;
    private boolean booked;

    public Seat() {}

    public Seat(int seatNumber, SeatType seatType, double price, boolean booked) {
        this.seatNumber = seatNumber;
        this.seatType = seatType == null ? SeatType.REGULAR : seatType;
        this.price = price;
        this.booked = booked;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        return String.format("Seat{num=%d, type=%s, price=%.2f, booked=%s}", seatNumber, seatType, price, booked);
    }
}
