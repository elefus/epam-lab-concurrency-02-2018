package lesson_06_02_2018;

import java.util.concurrent.TimeUnit;

@SuppressWarnings({"Duplicates", "InfiniteLoopStatement"})
public class Example4 {

    enum State {
        NOTHING,
        PRINT_VALUE,
        STOP
    }


    static volatile State state = State.NOTHING;

    public static void main(String[] args) throws InterruptedException {
        Thread counter = new Thread(() -> {
            int val = 0;
            loop: while (true) {
                switch (state) {
                    case NOTHING:
                        break;

                    case PRINT_VALUE:
                        System.out.println(val);
                        break;

                    case STOP:
                        break loop;
                }
                ++val;

            }
            System.out.println("Counter end");
        });
        counter.start();

        TimeUnit.SECONDS.sleep(1);

        state = State.PRINT_VALUE;

        TimeUnit.SECONDS.sleep(1);

        state = State.STOP;

        System.out.println("Main end");
    }
}
