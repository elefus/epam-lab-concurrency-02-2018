package lesson_02_02_2018;

import java.util.concurrent.TimeUnit;

public class Example4 {

    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> System.out.println("In thread " + t + " was caught " + e));

        ThreadGroup myGroup = new MyThreadGroup("myGroup");

        Runnable counter = () -> {
            System.out.println(Thread.currentThread());
            long value = 0;
            while (!Thread.interrupted()) {
                ++value;
                Thread.yield();
            }
            System.out.println(Thread.currentThread().getName() + " " + value);
        };

        Thread[] thread = new Thread[Runtime.getRuntime().availableProcessors() + 2];
        for (int i = 0; i < thread.length; ++i) {
            thread[i] = new Thread(myGroup, counter, "Counter-" + i);
            thread[i].setPriority(i < 2 ? Thread.MAX_PRIORITY - 3 : Thread.MAX_PRIORITY);
        }
        thread[0].setUncaughtExceptionHandler((t, e) -> System.out.println("Handler for the 0 thread"));

        for (Thread curr : thread) {
            curr.start();
        }

        TimeUnit.SECONDS.sleep(2);
        myGroup.interrupt();
    }

    private static class MyThreadGroup extends ThreadGroup {

        public MyThreadGroup(String name) {
            super(name);
        }

        @Override
        public void uncaughtException(Thread thread, Throwable exception) {
            System.out.println("Handler in group");
        }
    }
}
