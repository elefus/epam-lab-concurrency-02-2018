package lesson_09_02_2018;

public class Example2 {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
