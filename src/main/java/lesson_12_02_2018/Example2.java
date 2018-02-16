package lesson_12_02_2018;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class Example2 {

    private static class SingleThreadWorker {

        private final Queue<Runnable> tasks = new LinkedList<>();
        private final Thread worker;

        public SingleThreadWorker() {
            worker = new Thread(this::process, "Single-thread-worker");
            worker.start();
        }

        private void process() {
            try {
                while (!Thread.interrupted()) {
                    Runnable poll;
                    synchronized (tasks) {
                        while (tasks.isEmpty()) {
                            tasks.wait();
                        }
                        poll = tasks.remove();
                    }
                    poll.run();
                }
            } catch (InterruptedException ex) {
                System.out.println("SingleThreadWorker terminated");
            }
        }

        public void submit(Runnable task) {
            synchronized (tasks) {
                tasks.add(task);
                tasks.notify();
            }
        }

        public void shutdown() {
            worker.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SingleThreadWorker worker = new SingleThreadWorker();

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread() + " before sleeping");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread() + " after sleeping");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        worker.submit(task);
        worker.submit(task);
        worker.submit(task);

        new Thread(() -> {
            worker.submit(task);
            worker.submit(task);
            worker.submit(task);
        }).start();


        TimeUnit.SECONDS.sleep(10);

        worker.shutdown();
    }
}
