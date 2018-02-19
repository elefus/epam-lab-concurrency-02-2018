package lesson_19_02_2018;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class Example4 {

    public static void main(String[] args) {
        LongAdder adder = new LongAdder();
        adder.decrement();
        adder.increment();
        adder.add(10);

        LongAccumulator accumulator = new LongAccumulator(Long::sum, 0);
        accumulator.accumulate(10);
        accumulator.getThenReset();
        accumulator.reset();

    }
}
