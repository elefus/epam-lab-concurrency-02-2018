package lesson_12_02_2018;

import java.util.concurrent.TimeUnit;

public class Example1 {

    private static class SimpleWorker {

        public void process(Runnable task) {
            new Thread(task).start();
        }
    }

    public static void main(String[] args) {
        SimpleWorker simpleWorker = new SimpleWorker();

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread() + " before sleeping");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread() + " after sleeping");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        simpleWorker.process(task);
        simpleWorker.process(task);
        simpleWorker.process(task);
    }
}
