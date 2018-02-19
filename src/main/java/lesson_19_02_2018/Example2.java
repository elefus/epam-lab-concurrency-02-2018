package lesson_19_02_2018;

import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class Example2 {

    private static class ConcurrentStack<E> {

        private AtomicReference<Node<E>> head = new AtomicReference<>();


        // A - 0
        // B - 1
        public void push(E item) {
            Node<E> newHead = new Node<>(item);
            Node<E> oldHead;
            do {
                oldHead = head.get();
                // A - null
                // B - null
                newHead.next = oldHead;
                // B - waiting
            } while (!head.compareAndSet(oldHead, newHead));

            // null
        }

        public E pop() {
            Node<E> oldHead;
            Node<E> newHead;
            do {
                oldHead = head.get();
                if (oldHead == null) {
                    return null;
                }
                newHead = oldHead.next;
            } while (!head.compareAndSet(oldHead, newHead));
            return oldHead.item;
        }

        private static class Node<E> {
            private final E item;
            private Node<E> next;

            private Node(E item) {
                this.item = item;
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ConcurrentStack<Integer> stack = new ConcurrentStack<>();
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(2);

        service.execute(() -> {
            try {
                latch.countDown();
                latch.await();
                for (int i = 5; i < 10; ++i) {
                    stack.push(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();

        latch.countDown();
        latch.await();
        for (int i = 0; i < 5; ++i) {
            stack.push(i);
        }

        for (Integer element = stack.pop(); element != null; element = stack.pop()) {
            System.out.println(element);
        }

    }
}
