import java.util.ArrayList;
import java.util.List;

/**
 * VM Options: -Xmx100m -XX:+UseG1GC
 *
 * Чтобы увидеть разные сценарии:
 * -Xmx100m -XX:+UseG1GC -XX:+PrintGCDetails
 */
public class HeapOOM {

    static class BigObject {
        // Каждый объект занимает ~1MB данных
        byte[] data = new byte[1024 * 1024]; // 1 MB
        String name;

        BigObject(String name) {
            this.name = name;
        }
    }
    // 1. Создаем "подушку безопасности" (например, 5-10 МБ)
    private static byte[] reserve = new byte[1024 * 1024 * 10];


    public static void main(String[] args) {
        List<BigObject> list = new ArrayList<>();
        int counter = 0;

        try {
            while (true) {
                list.add(new BigObject("Object-" + counter));
                counter++;

                if (counter % 10 == 0) {
                    System.out.println("Created " + counter + " objects (~"
                            + (counter) + " MB)");
                    Thread.sleep(1000); // Даем GC шанс сработать
                }
            }
        } catch (OutOfMemoryError e) {
            reserve = null;
            System.err.println("\n=== OutOfMemoryError после "
                    + counter + " объектов ===");
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();

            // Анализ: сколько объектов выжило?
            System.out.println("\nObjects in list: " + list.size());
            System.out.println("Last object: " + list.get(list.size()-1).name);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}