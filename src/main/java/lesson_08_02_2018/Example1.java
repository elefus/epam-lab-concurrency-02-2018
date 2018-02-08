package lesson_08_02_2018;

@SuppressWarnings("Duplicates")
public class Example1 {

    private static volatile Integer value = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread inc = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                inc();
            }
        });

        Thread dec1 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                dec();
            }
        });

        Thread dec2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                dec();
            }
        });

        inc.start();
        dec1.start();
        dec2.start();

        inc.join();
        dec1.join();
        dec2.join();

        System.out.println(value);
    }

    private static void inc() {
        synchronized (Example1.class) {
            ++value;
        }
    }

    private synchronized static void dec() {
        --value;
    }
}

// inc            dec1             dec2          Example1.class
//                                                    ()
//  +
//                 ~
//                                  ~
//  -                                                 ()