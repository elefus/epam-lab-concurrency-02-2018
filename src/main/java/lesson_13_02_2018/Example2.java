package lesson_13_02_2018;

@SuppressWarnings("Duplicates")
public class Example2 {

    private static class SingleThreadWorker {

        private static final Runnable POISON_PILL = () -> {};
        private final BlockingQueue<Runnable> tasks = new BlockingQueue<>(10);
        private final Thread worker;
        private volatile boolean isShutdown;

        public SingleThreadWorker() {
            worker = new Thread(this::process, "Single-thread-worker");
            worker.start();
        }

        private void process() {
            try {
                while (!Thread.interrupted()) {
                    Runnable task = tasks.take();
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
            tasks.put(task);
        }

        public void shutdown() {
            if (!isShutdown) {
                synchronized (tasks) {
                    if (!isShutdown) {
                        isShutdown = true;
                        tasks.put(POISON_PILL);
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
