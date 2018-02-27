package lesson_08_02_2018.philosophers;

import lombok.Data;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Table {

    @Data
    static class Seat {
        Stick leftStick;
        Stick rightStick;

        Seat(Stick leftStick, Stick rightStick) {
            this.leftStick = leftStick;
            this.rightStick = rightStick;
        }
    }

    Map<Philosopher, Seat> seats = new HashMap<>();
    List<Stick> sticks = new ArrayList<>();
    int seatsLimit;

    Table(int seatsLimit) {
        this.seatsLimit = seatsLimit;
    }

    public boolean addGuest(Philosopher philosopher) {
        if (seats.size() >= seatsLimit) {
            return false;
        }
        Stick leftStick = seats.isEmpty() ?
                addNewStick() :
                sticks.get(sticks.size()-1);
        Stick rightStick = (seats.size() == seatsLimit - 1) ?
                sticks.get(0) :
                addNewStick();
        return seats.putIfAbsent(philosopher, new Seat(leftStick, rightStick)) == null;
    }

    private Stick addNewStick() {
        Stick stick = new Stick();
        sticks.add(stick);
        return stick;
    }

    private BiFunction<Philosopher, Function<Seat, Stick>, Stick> getStick =
            ((philosopher, seatStickFunction) -> {
                Seat seat = seats.get(philosopher);
                if (seat == null) {
                    return null;
                }
                return seatStickFunction.apply(seat);
            });

    public Stick getLeftStick(Philosopher philosopher) {
        return getStick.apply(philosopher, Seat::getLeftStick);
    }

    public Stick getRightStick(Philosopher philosopher) {
        return getStick.apply(philosopher, Seat::getRightStick);
    }
}
