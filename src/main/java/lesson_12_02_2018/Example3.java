package lesson_12_02_2018;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Example3 {


    public static void main(String[] args) {
        ExecutorService service = null;

        Future<?> runnableFuture = service.submit(() -> {
            System.out.println("I'm runnable");
        });


        Future<Long> runnableWithResultFuture = service.submit(() -> {
            System.out.println("I', runnable with second param");
        }, 1234L);

        Future<Integer> submit = service.submit(() -> 42);

    }
}
