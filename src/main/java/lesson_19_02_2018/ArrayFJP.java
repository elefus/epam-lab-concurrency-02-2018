package lesson_19_02_2018;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ArrayFJP {

    public static final int ARRAY_SIZE = 1_000_000;

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        int[] array = new int[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = i + 1;
        }
        ArraySumTask arraySumTask = new ArraySumTask(array, 0, array.length);

        System.out.println(forkJoinPool.invoke(arraySumTask));

    }

    static class ArraySumTask extends RecursiveTask<Long> {
        public static final double THRESHOLD = 10;
        private final int left;
        private final int right;
        private final int[] array;

        public ArraySumTask(int[] array, int left, int right) {
            this.left = left;
            this.right = right;
            this.array = array;
        }

        @Override
        protected Long compute() {
            if (right - left <= THRESHOLD) {
                long sum = 0;
                for (int i = left; i < right; i++) {
                    sum += array[i];
                }
                return sum;
            }
            int mid = (left + right) >>> 1;
            ForkJoinTask<Long> leftTask = new ArraySumTask(array, left, mid);
            ForkJoinTask<Long> rightTask = new ArraySumTask(array, mid, right);

            leftTask.fork();
            rightTask.fork();
            long result = 0;
            result += rightTask.join();
            result += leftTask.join();
            return result;
        }
    }
}

