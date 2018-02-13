package lesson_13_02_2018;

public class Example3Synchro {

    public static void main(String[] args) {
        Object lock = new Object();

        synchronized (lock) { // lock monitor
            // critical section
        } // release monitor
    }
}
