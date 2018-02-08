package lesson_08_02_2018.philosophers;

class Stick {}

class Philosopher implements Runnable {

    private static int counter;

    private String name;
    private Stick leftStick;
    private Stick rightStick;

    public Philosopher(Stick leftStick, Stick rightStick) {
        name = ("Philosopher-" + ++counter);
        this.leftStick = leftStick;
        this.rightStick = rightStick;
    }

    @Override
    public void run() {
        while (true) {
            think();
            grabLeftStick();
            grabRightStick();
            eat();
            putDownLeftStick();
            putDownRightStick();
        }
    }

    private void think() {
        System.out.println(name + " is thinking");
    }

    private void eat() {
        System.out.println(name + " is eating");
    }

    private void grabLeftStick() {
        synchronized (leftStick) {
            System.out.println(name + ": grab left stick");
        }
    }

    private void grabRightStick() {
        synchronized (rightStick) {
            System.out.println(name + ": grab right stick");
        }
    }

    private void putDownLeftStick() {
        System.out.println(name + ": put down left stick");
    }

    private void putDownRightStick() {
        System.out.println(name + ": put down right stick");
    }
}

public class Main {
    public static void main(String[] args) {
        Stick[] sticks = new Stick[5];
        for (int i = 0; i < sticks.length; i++) {
            sticks[i] = new Stick();
        }

        Philosopher[] philosophers = new Philosopher[5];

        for (int i = 0; i < philosophers.length; i++) {
            Stick leftStick = sticks[i];
            Stick rightStick = sticks[(i + 1) % 5];

            philosophers[i] = new Philosopher(leftStick, rightStick);

            new Thread(philosophers[i]).start();
        }
    }
}
