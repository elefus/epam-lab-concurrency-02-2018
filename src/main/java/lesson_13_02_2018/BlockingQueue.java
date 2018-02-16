package lesson_13_02_2018;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<E> {

    private final Queue<E> elements = new LinkedList<>();
    private final int size;

    public BlockingQueue(int size) {
        this.size = size;
    }

    public void put(E element) {
        synchronized (elements) {
            elements.add(element);
            elements.notify();
        }
    }

    public E take() throws InterruptedException {
        synchronized (elements) {
            while (elements.isEmpty()) {
                elements.wait();
            }
            return elements.remove();
        }
    }
}
