package lesson_15_02_2018;

import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.*;

public class Example1 {

    @ToString
    private static class PlateWithNote implements Comparable<PlateWithNote> {

        @Getter
        private final String dish;
        private final long deliveryTime;


        private PlateWithNote(String dish, long delay, TimeUnit unit) {
            this.dish = dish;
            this.deliveryTime = System.currentTimeMillis() + unit.toMillis(delay);
        }

        @Override
        public int compareTo(PlateWithNote other) {
            return Long.compare(deliveryTime, other.deliveryTime);
        }
    }

    private static Runnable getChefBehaviour(BlockingQueue<? super PlateWithNote> window) {
        return () -> {
            try {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                for (int i = 0; i < 10; ++i) {
                    System.out.println("Before cooking");
                    window.put(new PlateWithNote(String.valueOf(i), random.nextInt(0, 100), TimeUnit.SECONDS));
                    System.out.println("Cooking completed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private static Runnable getWaiterBehaviour(BlockingQueue<? extends PlateWithNote> window) {
        return () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                for (int i = 0; i < 10; ++i) {
                    System.out.println("Waiting for dish");

                    PlateWithNote plate = window.take();

                    System.out.println(Thread.currentThread().getName() + " took the: " + plate);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        BlockingQueue<PlateWithNote> window = new PriorityBlockingQueue<>();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(getChefBehaviour(window));
        service.execute(getWaiterBehaviour(window));
        service.shutdown();
    }
}
