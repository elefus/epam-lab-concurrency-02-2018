package lesson_20_02_2018;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class FJPSumIntArrayExercise {

    public static void main(String[] args) {
        // TODO
    }


    private static class ForkJoinSumArrayTask extends RecursiveTask<Integer> {

        private static final int SEQUENTIAL_THRESHOLD = 30;

        private int[] data;
        private int fromInclusive;
        private int toExclusive;

        public ForkJoinSumArrayTask(int[] data, int fromInclusive, int toExclusive) {
            this.fromInclusive = fromInclusive;
            this.toExclusive = toExclusive;
            this.data = data;
        }

        @Override
        protected Integer compute() {
            if (toExclusive - fromInclusive < SEQUENTIAL_THRESHOLD) {
                return Arrays.stream(data)
                             .skip(fromInclusive)
                             .limit(toExclusive - fromInclusive)
                             .sum();
            }


            int mid = (fromInclusive + toExclusive) / 2;

            ForkJoinSumArrayTask left = new ForkJoinSumArrayTask(data, fromInclusive, mid);
            left.fork();


            return new ForkJoinSumArrayTask(data, mid, toExclusive).compute() + left.join();
        }
    }
}