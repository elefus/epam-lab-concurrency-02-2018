package lesson_15_02_2018;

import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.*;

public class Example2 {

    private interface Plate {

        String getDish();
    }

    @ToString
    private static class PlateWithNote implements Plate, Delayed {

        @Getter
        private final String dish;
        private final long deliveryTimeMillis;


        private PlateWithNote(String dish, long delay, TimeUnit unit) {
            this.dish = dish;
            this.deliveryTimeMillis = System.currentTimeMillis() + unit.toMillis(delay);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(deliveryTimeMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed other) {
            return Long.compare(deliveryTimeMillis, other.getDelay(TimeUnit.MILLISECONDS));
        }
    }

    private static Runnable getChefBehaviour(BlockingQueue<? super PlateWithNote> window) {
        return () -> {
            try {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                for (int i = 0; i < 10; ++i) {
                    System.out.println("Before cooking");
                    TimeUnit.SECONDS.sleep(3);
                    window.put(new PlateWithNote(String.valueOf(i), 5, TimeUnit.SECONDS));
                    System.out.println("Cooking completed: " + i + " " + System.currentTimeMillis());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private static Runnable getWaiterBehaviour(BlockingQueue<? extends Plate> window) {
        return () -> {
            try {
                for (int i = 0; i < 10; ++i) {
                    System.out.println("Waiting for dish");

                    Plate plate = window.take();

                    System.out.println(Thread.currentThread().getName() + " took the: " + plate);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        BlockingQueue<PlateWithNote> window = new DelayQueue<>();

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(getChefBehaviour(window));
        service.execute(getWaiterBehaviour(window));
        service.shutdown();
    }
}
