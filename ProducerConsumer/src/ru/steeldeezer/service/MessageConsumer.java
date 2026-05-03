package ru.steeldeezer.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import ru.steeldeezer.model.Message;
public class MessageConsumer implements Runnable {
    private static AtomicInteger count = new AtomicInteger(0);
    private final BlockingQueue<Message> buffer;

    public MessageConsumer(BlockingQueue<Message> buffer ){
        this.buffer = buffer;
        count.getAndIncrement();
    }

    public static AtomicInteger getCount() {
        return count;
    }

    @Override
    public void run(){
        try {
            while (true) {
                Message msg = buffer.take();
                if(msg.getMessage().equals("POISON_PILL")){
                    System.out.println(Thread.currentThread().getName() + " received poison pill, stopping...");
                    break;
                }
                System.out.println("📥 Потреблено: " + msg);
                Thread.sleep(500);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }


}
