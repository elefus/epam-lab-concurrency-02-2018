package lesson_01_02_2018;

import java.util.concurrent.TimeUnit;

public class Example1 {

    public static void main(String[] args) throws InterruptedException {
        Thread currentThread = Thread.currentThread();

        System.out.println(currentThread.getName());
        System.out.println(currentThread.getPriority());
        System.out.println(currentThread.getThreadGroup());

        System.out.println(currentThread);

        TimeUnit.SECONDS.sleep(20);
    }
}
