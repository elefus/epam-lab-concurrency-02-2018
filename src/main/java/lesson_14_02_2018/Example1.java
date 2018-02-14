package lesson_14_02_2018;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Example1 {

    public static void main(String[] args) {
        Queue<Integer> synchroQueue = new LinkedBlockingQueue<>();

        List<Integer> concurrentQueue = Collections.synchronizedList(new LinkedList<>());
    }
}
