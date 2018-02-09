package lesson_09_02_2018.philosophers;

import java.util.concurrent.TimeUnit;

public class Restaurateur {

    private static Table table = new Table();

    public static void main(String[] args) throws InterruptedException {
        Thread restaurateur = Thread.currentThread();
        restaurateur.setName("Restaurateur");

        createPhilosopher("Kun", restaurateur);
        createPhilosopher("Lao", restaurateur);
        createPhilosopher("Mo", restaurateur);

        TimeUnit.SECONDS.sleep(3);

        System.out.println("Начало обеда");

        synchronized (table) {
            System.out.println("Before notifyAll");
            table.notifyAll();
            System.out.println("After notifyAll");
        }

        while (true) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("123");
        }
    }

    private static void createPhilosopher(String name, Thread restorator) {
        Philosopher philosopher = new Philosopher(name);
        philosopher.setBehaviour(() -> {
            try {
                synchronized (table) {
                    System.out.println("Before wait " + name);
                    table.wait();
                    System.out.println("After wait " + name);
                }
                synchronized (table.getStickWithLowestIndex(philosopher)) {
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (table.getStickWithHighestIndex(philosopher)) {
                        System.out.println(philosopher + " eating");
                        TimeUnit.SECONDS.sleep(10);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        philosopher.inviteTo(table);
    }
}
