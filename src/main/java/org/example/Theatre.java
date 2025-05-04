package org.example;

import java.util.TreeSet;

public class Theatre {
    private String theatreName;
    private int seatsPerRow;
    private TreeSet<Seat> seats;

    public Theatre(String theatreName, int numRows, int seatsPerRow) {
        if (numRows > 26) {
            throw new IllegalArgumentException("Exceeded maximum number of rows - 26");
        }
        this.theatreName = theatreName;
        this.seatsPerRow = seatsPerRow;
        this.seats = new TreeSet<>();

        for (char row = 'A'; row < 'A' + numRows; row++) {
            for (int seatNumber = 1; seatNumber <= seatsPerRow; seatNumber++) {
                seats.add(new Seat(row, seatNumber));
            }
        }
    }

    public boolean reserveSeat(String seatId) {
        for (Seat seat : seats) {
            if (seat.getSeatId().equals(seatId)) {
                if (!seat.isReserved()) {
                    seat.setReserved(true);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean reserveSeats(int numSeats, char minRow, char maxRow, int minSeatNum, int maxSeatNum) {
        if (minRow > maxRow || minSeatNum > maxSeatNum || numSeats <= 0) {
            return false;
        }

        for (char row = minRow; row <= maxRow; row++) {
            int contiguousAvailable = 0;
            int startSeatNum = -1;

            for (int seatNum = minSeatNum; seatNum <= maxSeatNum; seatNum++) {
                String seatId = String.format("%c%03d", row, seatNum);
                Seat seat = findSeat(seatId);

                if (seat != null && !seat.isReserved()) {
                    contiguousAvailable++;
                    if (startSeatNum == -1) {
                        startSeatNum = seatNum;
                    }
                    if (contiguousAvailable >= numSeats) {
                        for (int i = 0; i < numSeats; i++) {
                            String reserveId = String.format("%c%03d", row, startSeatNum + i);
                            reserveSeat(reserveId);
                        }
                        return true;
                    }
                } else {
                    contiguousAvailable = 0;
                    startSeatNum = -1;
                }
            }
        }
        return false; // Cannot find contiguous seats
    }

    private Seat findSeat(String seatId) {
        for (Seat seat : seats) {
            if (seat.getSeatId().equals(seatId)) {
                return seat;
            }
        }
        return null;
    }

    public void printSeatMap() {
        System.out.printf("Seat Map for %s: %n", theatreName);

        char currentRow = 'A';
        boolean firstSeatInRow = true;

        for (Seat seat : seats) {
            if (seat.getRow() != currentRow) {
                System.out.println();
                currentRow = seat.getRow();
                firstSeatInRow = true;
            }

            if (!firstSeatInRow) {
                System.out.print(" ");
            }
            System.out.print(seat);
            firstSeatInRow = false;
        }
        System.out.println();
    }

    public class Seat implements Comparable<Seat> {
        private char row;
        private int seatNumber;
        private boolean reserved;

        public Seat(char row, int seatNumber) {
            this.row = row;
            this.seatNumber = seatNumber;
            this.reserved = false;
        }

        public String getSeatId() {
            return String.format("%c%03d", row, seatNumber);
        }

        public char getRow() {
            return row;
        }

        public int getSeatNumber() {
            return seatNumber;
        }

        public boolean isReserved() {
            return reserved;
        }

        public void setReserved(boolean reserved) {
            this.reserved = reserved;
        }

        @Override
        public int compareTo(Seat other) {
            if (this.row != other.row) {
                return this.row - other.row;
            }
            return this.seatNumber - other.seatNumber;
        }

        @Override
        public String toString() {
            return reserved ? "(" + getSeatId() + ")": getSeatId();
        }
    }
}
