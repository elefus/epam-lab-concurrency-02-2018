package lesson_06_02_2018;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class Example5 {

    static int val;

    public static void main(String[] args) throws InterruptedException {
        Thread counter = new Thread(() -> {
            for (int i = 0; i < 3; ++i) {
                ++val;
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



            for (int i = 0; i < 3; ++i) {
                ++val;
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(val);
            System.out.println("Counter end");
        });
        counter.start();


        //TODO sleep val == 3
        System.out.println(val);
        System.out.println("Main end");
    }
}
