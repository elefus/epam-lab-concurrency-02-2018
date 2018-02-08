package lesson_07_02_2018;

@SuppressWarnings("Duplicates")
public class Example1 {

    private static volatile Integer value = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread inc = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                synchronized (value) {
                    ++value;
                    // ...
                }
            }
        });

        Thread dec = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                synchronized (value) {
                    --value;
                }
            }
        });

        inc.start();
        dec.start();

        inc.join();
        dec.join();

        System.out.println(value);
    }
}