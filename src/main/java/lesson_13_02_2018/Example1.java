package lesson_13_02_2018;

import lombok.Synchronized;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class Example1 {

    private static class SingleThreadWorker {

        private static final Runnable POISON_PILL = () -> {};
        private final Queue<Runnable> tasks = new LinkedList<>();
        private final Thread worker;
        private volatile boolean isShutdown;

        public SingleThreadWorker() {
            worker = new Thread(this::process, "Single-thread-worker");
            worker.start();
        }

        private void process() {
            try {
                while (!Thread.interrupted()) {
                    Runnable task;
                    synchronized (tasks) {
                        while (tasks.isEmpty()) {
                            tasks.wait();
                        }
                        task = tasks.remove();
                    }
                    if (task == POISON_PILL) {
                        return;
                    }
                    task.run();
                }
            } catch (InterruptedException ex) {
                System.out.println("SingleThreadWorker terminated");
            }
        }

        public void submit(Runnable task) {
            if (isShutdown) {
                throw new IllegalStateException();
            }
            synchronized (tasks) {
                tasks.add(task);
                tasks.notify();
            }
        }

        public void shutdown() {
            if (!isShutdown) {
                synchronized (tasks) {
                    if (!isShutdown) {
                        isShutdown = true;
                        tasks.add(POISON_PILL);
                    }
                }
            }
        }

        public void shutdownNow() {

        }
    }

    public static void main(String[] args) throws InterruptedException {
//        SingleThreadWorker worker = new SingleThreadWorker();
//
//        Runnable task = () -> {
//            try {
//                System.out.println(Thread.currentThread() + " before sleeping");
//                TimeUnit.SECONDS.sleep(1);
//                System.out.println(Thread.currentThread() + " after sleeping");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        };
//        worker.submit(task);
//        worker.submit(task);
//        worker.submit(task);
//
//        new Thread(() -> {
//            worker.submit(task);
//            worker.submit(task);
//            worker.submit(task);
//        }).start();
//
//
//        TimeUnit.SECONDS.sleep(10);
//
//        worker.shutdown();

    }
}
