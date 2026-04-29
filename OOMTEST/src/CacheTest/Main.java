package CacheTest;

import java.util.Collections;
import java.util.Map;

public class Main {
    private static Map<Integer, String> cache = Collections.synchronizedMap(new LimitedCache<>(10_000));
    public static void main(String[] args) {
        for (int i = 0; i < 1_000_000; i++) {
            Integer key = new Integer(i);
            cache.put(key, "value" + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }

        }

        System.gc();

        System.out.println("Size: " + cache.size());
    }
}