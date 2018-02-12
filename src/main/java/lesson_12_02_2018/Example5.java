package lesson_12_02_2018;

import lombok.SneakyThrows;
import lombok.Synchronized;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Example5 {

    private static class Storage {

        private int a;
        private int b;

        @Synchronized
        void action() {
            try {
                a++;
                TimeUnit.SECONDS.sleep(3);
                b--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Synchronized
        public int[] getValues() {
            return new int[]{a, b};
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Storage storage = new Storage();

        Runnable task = () -> {
            for (int i = 0; i < 10; ++i) {
                storage.action();
            }
        };

        Thread thread = new Thread(task);
        thread.start();
        new Thread(task).start();
        new Thread(task).start();

        TimeUnit.SECONDS.sleep(2);

        thread.stop();


        System.out.println(Arrays.toString(storage.getValues()));
    }
}
