package HeapOomTest;

import java.util.ArrayList;
import java.util.List;

public class LeakyApp {
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Приложение запущено. PID: " + ProcessHandle.current().pid());

        int counter = 0;
        while (true) {
            // Добавляем огромные строки в статический список (никогда не удаляются)
            list.add("Очень длинная строка номер " + counter++ + " ".repeat(10000));

            if (counter % 100 == 0) {
                System.out.println("Добавлено " + counter + " объектов. Память тает...");
            }

            Thread.sleep(50); // чтобы не мгновенно упасть
        }
    }
}