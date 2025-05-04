package org.example;

public class Main {
    public static void main(String[] args) {
        Theatre theatre = new Theatre("Test Theatre", 5, 12);
        theatre.printSeatMap();

        theatre.reserveSeat("A002");
        theatre.reserveSeat("C005");

        // Reserve 3 contiguous seats in rows A-C, seats 1-10
        theatre.reserveSeats(8, 'C', 'D', 2, 9);

        System.out.println("\nAfter reservations:");
        theatre.printSeatMap();
    }
}