import ru.steeldeezer.model.Message;
import ru.steeldeezer.service.MessageConsumer;
import ru.steeldeezer.service.Producer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BlockingQueue<Message> buffer = new ArrayBlockingQueue<>(5);
        MessageConsumer messageConsumer = new MessageConsumer(buffer);
        MessageConsumer messageConsumer2 = new MessageConsumer(buffer);

        Producer producer = new Producer(buffer,20, MessageConsumer.getCount().get());


        Thread prodecerThread = new Thread(producer);
        Thread consumerThread1 = new Thread(messageConsumer);
        Thread consumerThread2 = new Thread(messageConsumer2);


        prodecerThread.start();
        consumerThread1.start();
        consumerThread2.start();

    }
}