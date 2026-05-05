package ru.steelDeezer.service;

import ru.steelDeezer.model.Message;

import java.util.Queue;

public class Producer implements Runnable {
    private  final Queue<Message> buffer;
    private  final int messageCount;
    private  final int consumerCount;
    private final int capacity;

    public Producer(Queue<Message> buffer, int messageCount,int consumerCount, int capacity){
        this.buffer = buffer;
        this.messageCount = messageCount;
        this.consumerCount = consumerCount;
        this.capacity = capacity;
    }


    @Override
    public void run(){
        try {
            for (int i = 1; i < messageCount; i++) {
                synchronized (buffer){
                    while (buffer.size() == capacity){
                        buffer.wait();
                    }
                    Message msg = new Message("Message " + i);
                    buffer.add(msg);
                    System.out.println(Thread.currentThread().getName() + " produced: " + msg);
                    buffer.notifyAll();
                }
                Thread.sleep(100);
            }
            synchronized (buffer){
                for (int j = 0; j < consumerCount; j++) {
                    buffer.add(new Message("POISON_PILL"));
                }
                buffer.notifyAll();
                System.out.println("ru.steelDeezer.service.Producer finished");
            }
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
