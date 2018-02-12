package lesson_09_02_2018.exhibition;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Storage {

    private String value = "DEFAULT";
    private final Object readMonitor = new Object();
    private final Object writeMonitor = new Object();
    private final Object mutex1 = new Object();
    private final Object mutex2 = new Object();

    private int readCount=0;
    private int writeCount=0;


    String read() {
        String val = "";


        try {
            synchronized (readMonitor) {
                synchronized (mutex1) {
                    readCount++;
                    System.out.println(readCount);
                    if (readCount == 1) {
                        synchronized(writeMonitor) {writeMonitor.wait();}
                    }
                }
            }

            TimeUnit.SECONDS.sleep(1);
            val = value;

            synchronized (mutex1) {
                readCount--;
                if (readCount == 0) {
                    synchronized(writeMonitor) {writeMonitor.notify();}
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return val;

    }

    void write(String value) {
        System.out.println("");

        try {
            synchronized (mutex2) {
                writeCount++;
                if (writeCount == 1) {
                    synchronized (readMonitor) {
                        readMonitor.wait();
                    }
                }
            }

            synchronized (writeMonitor) { writeMonitor.wait();}
                this.value = value;
            synchronized (writeMonitor) { writeMonitor.notify();}


            synchronized (mutex2) {
                writeCount--;
                if (writeCount == 0) {
                    synchronized(readMonitor) {readMonitor.notify();}
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
