package lesson_13_02_2018;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Example3 {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = null;

        lock.lock();   // lock
        try {
            if (Thread.interrupted()) {
                // todo
            }
            // critical section
        } finally {
            lock.unlock(); // release lock
        }



        try {
            lock.lockInterruptibly();  // lock

            // critical section


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();   // release lock
        }

        while (true) {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                // critical section
                break;
            } else {
                // other code
            }
        }

        Condition firstCondition = lock.newCondition();
        Condition secondCondition = lock.newCondition();


    }
}
