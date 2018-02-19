package lesson_19_02_2018;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Example3 {

    public static void main(String[] args) {
        String str = "123";
        AtomicMarkableReference<String> atomic = new AtomicMarkableReference<>(str, true);

        boolean[] holder = {true};
        String actual = atomic.get(holder);


        AtomicStampedReference<String> atomicStamped = new AtomicStampedReference<>(str, 0);
        // solver ABA problem
    }
}
