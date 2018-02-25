package lesson_08_02_2018.philosophers;

import java.util.Arrays;
import java.util.List;

public class Restarateur {
    public static void main(String[] args) {
        Philosopher[] philosophers = {new Philosopher("One"), new Philosopher("Two"), new Philosopher("Three"), new Philosopher("Four")};
        Table table = new Table(philosophers.length);
        for (Philosopher philosopher : philosophers) {
            philosopher.getToTable(table);
        }
    }
}
