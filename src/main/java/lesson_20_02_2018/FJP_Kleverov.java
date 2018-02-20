package lesson_20_02_2018;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class FJP_Kleverov {

     public static void main(String[] args) {

        int[] data = ThreadLocalRandom.current()
                                      .ints(40,0,100)
                                      .toArray();
        long expected = Arrays.stream(data).sum();
        long result =  new ForkJoinPool().invoke(new ForkJoinSumTask(data,0,data.length));
        if (expected != result) {
            throw new RuntimeException("wrong");
        }




    }

    public static class ForkJoinSumTask extends RecursiveTask<Long> {
        private int[] data;
        private  static final int SEQUENTIAL_THRESHOLD = 10;
        private  int left;
        private  int right;
        private  long result;

        public ForkJoinSumTask(int[] data, int left, int right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        protected Long compute() {
            if (left-right < SEQUENTIAL_THRESHOLD){
               
                for (int i = left; i <right ; i++) {
                    result += data[i];
                }
            }
            else {
                ForkJoinSumTask leftTask = new ForkJoinSumTask(data,left, left+right>>>1);
                ForkJoinSumTask rightTask = new ForkJoinSumTask(data,left+right>>>1, right);

                leftTask.fork();
                rightTask.fork();


                Long rightResult = rightTask.join();
                Long leftResult = leftTask.join();

                result = rightResult+ leftResult;



            }
            return result;
        }
    }






}
