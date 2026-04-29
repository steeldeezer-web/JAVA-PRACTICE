package MultiThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Counter3 {
    private static int count =  0;
    private static final ReentrantLock lock = new ReentrantLock();

    public static void increment() {
        lock.lock();
        try {
            count++;

        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increment();
                }
            });
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Expected: 100000, Got: " + count);
    }
}