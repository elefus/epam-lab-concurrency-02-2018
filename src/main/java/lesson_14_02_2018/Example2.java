package lesson_14_02_2018;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class Example2 {


    public static void main(String[] args) {
        BlockingQueue<String> window = new SynchronousQueue<>(true);

        Thread chef = new Thread(() -> {
            try {
                for (int i = 0; i < 10; ++i) {
                    System.out.println("Before cooking");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("Cooking completed");
                    window.put(String.valueOf(i));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "chef");

        Runnable waiterBehaviour = () -> {
            try {
                for (int i = 0; i < 10; ++i) {
                    System.out.println("Waiting for dish");

                    String dish = window.take();

                    System.out.println(Thread.currentThread().getName() + " dish has been taken " + dish);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread waiter1 = new Thread(waiterBehaviour, "waiter-1");
        Thread waiter2 = new Thread(waiterBehaviour, "waiter-2");
        Thread waiter3 = new Thread(waiterBehaviour, "waiter-3");

        chef.start();
        waiter1.start();
        waiter2.start();
        waiter3.start();
    }
}
