package lesson_14_02_2018;

import java.util.concurrent.*;

@SuppressWarnings("Duplicates")
public class Example3 {

    public static void main(String[] args) {
        BlockingQueue<String> window = new SynchronousQueue<>(true);

        Callable<Object> callableWithNullAsResult = Executors.callable(() -> System.out.println("123"));
        Callable<Integer> callableWithResult = Executors.callable(() -> System.out.println("123"), 101);

        ExecutorService service = getService();

        Runnable chefBehaviour = () -> {
            try {
                for (int i = 0; i < 10; ++i) {
                    System.out.println("Before cooking");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Cooking completed");
                    window.put(String.valueOf(i));
                }
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        };
        service.execute(chefBehaviour);

        Runnable waiterBehaviour = () -> {
            try {
                for (int i = 0; i < 10; ++i) {
                    System.out.println("Waiting for dish");

                    String dish = window.take();

                    System.out.println(Thread.currentThread().getName() + " dish has been taken " + dish);
                }
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        };

        service.execute(waiterBehaviour);
        service.execute(waiterBehaviour);
        service.execute(waiterBehaviour);
        service.execute(waiterBehaviour);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(service::shutdownNow, 14, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(scheduledExecutorService::shutdown, 15, TimeUnit.SECONDS);
    }

    private static ExecutorService getService() {
        int numberCores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor result = new ThreadPoolExecutor(
                numberCores,
                numberCores * 2,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                r -> {
                    System.out.println("Thread created");
                    Thread thread = new Thread(r);
                    thread.setName("waiter");
                    return thread;
                }, (r, executor) -> System.err.println("Rejected"));
        return Executors.unconfigurableExecutorService(result);
    }
}
