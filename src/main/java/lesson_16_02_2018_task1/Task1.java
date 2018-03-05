package lesson_16_02_2018_task1;

import java.util.concurrent.*;

public class Task1 {

    public static Runnable getParticipantBehaiviour(int number , CountDownLatch signal, CountDownLatch arrived){
        return () -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();


            try {
                signal.await();
                int speed =  random.nextInt(1, 10);
                System.out.println(number + ": starting with speed " + (10-speed));
                TimeUnit.SECONDS.sleep(speed);
                System.out.println(number + ": arrived");
                arrived.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }


    public static void main(String[] args) {
        int N = 5;

        CountDownLatch signal = new CountDownLatch(3);

        CountDownLatch arrived = new CountDownLatch(N);
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < N; i++) {
            service.execute(getParticipantBehaiviour(i,signal,arrived));
        }
        service.shutdown();
        System.out.println("Ready");
        signal.countDown();
        System.out.println("Steady");
        signal.countDown();
        System.out.println("Medvedi");
        signal.countDown();

        try {
            arrived.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads arrived.");





    }
}
