package lesson_08_02_2018.philosophers;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Restarateur {

        private  static  Philosopher[] philosophers = {new Philosopher("One"), new Philosopher("Two"), new Philosopher("Three"), new Philosopher("Four"), new Philosopher("Five")};
        private  static  Table table = new Table(philosophers.length);

    public static void main(String[] args) throws InterruptedException {
        for (Philosopher philosopher : philosophers) {
            philosopher.getToTable(table);
        }

        allowEating(0);
        allowEating(2);
        TimeUnit.SECONDS.sleep(3);
        allowEating(1);
        allowEating(3);
        TimeUnit.SECONDS.sleep(3);
        allowEating(4);
    }

    private static  void allowEating(int i) {
        Mutex mutex = philosophers[i].getCanEat();
        mutex.setAllowed(true);
        synchronized (mutex) {
            mutex.notify();
        }

    }
}
