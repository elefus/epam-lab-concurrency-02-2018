package lesson_14_02_2018;

import lombok.Data;
import lombok.Setter;

import java.util.concurrent.*;

@SuppressWarnings("Duplicates")
public class Example4 {

    @Data
    @Setter
    public static class Plate {

        String dish;
    }

    public static void main(String[] args) {
        Exchanger<Plate> window = new Exchanger<>();

        ExecutorService service = Executors.newCachedThreadPool();

        Plate plate1 = new Plate();
        Plate plate2 = new Plate();

        Runnable chefBehaviour = () -> {
            try {
                for (int i = 0; i < 10; ++i) {
                    System.out.println("Before cooking");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Cooking completed");
                    plate1.setDish(String.valueOf(i));
                    Plate emptyPlate = window.exchange(plate1);
                }
            } catch (InterruptedException ignored) {
            }
        };
        service.execute(chefBehaviour);

        Runnable waiterBehaviour = () -> {
            try {
                for (int i = 0; i < 10; ++i) {
                    System.out.println("Waiting for dish");

                    Plate fullPlate = window.exchange(plate2);

                    System.out.println(Thread.currentThread().getName() + " dish has been taken " + fullPlate);
                }
            } catch (InterruptedException ignored) {
            }
        };

        service.execute(waiterBehaviour);
    }
}
