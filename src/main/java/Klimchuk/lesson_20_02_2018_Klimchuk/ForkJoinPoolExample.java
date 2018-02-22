package Klimchuk.lesson_20_02_2018_Klimchuk;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolExample {

    public static void main(String[] args) {

        int[] veryBigArray = new int[1_000_000];
        Arrays.fill(veryBigArray, 1);
        long expected = Arrays.stream(veryBigArray).sum();

        ForkJoinPool fjp = new ForkJoinPool();
        long result = fjp.invoke(new ForkJoinPoolSumTask(veryBigArray));

        if( expected != result ) {
            throw new RuntimeException("ERROR");
        }

        System.out.println("Sum is " + result);
    }

    public static class ForkJoinPoolSumTask extends RecursiveTask<Long> {

        private final int[] data;
        private static final int THRESHOLD = 10;    

        public ForkJoinPoolSumTask(int[] data) {
            this.data = data;
        }

        @Override
        protected Long compute() {
            long result = 0L;

            if(data.length < THRESHOLD) {

                result += Arrays.stream(data).sum();

            }

            else {

                int mid = data.length >>> 1;

                ForkJoinTask<Long> firstTask = new ForkJoinPoolSumTask(Arrays.copyOfRange(data, 0, mid));
                ForkJoinTask<Long> secondTask = new ForkJoinPoolSumTask(Arrays.copyOfRange(data, mid, data.length));

                ForkJoinTask.invokeAll(firstTask, secondTask);

                result += secondTask.join();
                result += firstTask.join();

            }

            return result;
        }
    }

}
