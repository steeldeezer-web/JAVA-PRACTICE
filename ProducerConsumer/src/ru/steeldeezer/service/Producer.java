package ru.steeldeezer.service;
import  ru.steeldeezer.model.Message;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{
    private final int messageCount;
    private final int consummerCount;
    private final BlockingQueue<Message> buffer;

    public Producer(BlockingQueue<Message> buffer, int messageCount, int consummerCount){
        this.buffer = buffer;
        this.messageCount = messageCount;
        this.consummerCount = consummerCount;
    }
    @Override
    public void run(){
        try{
            for (int i = 0; i < messageCount; i++) {
                Message msg = new Message("Message" + i);
                buffer.put(msg);
                System.out.println("✅ Произведено: " + msg);
                Thread.sleep(2000);

            }
            for (int i = 0; i < consummerCount; i++) {
                buffer.put(new Message("POISON_PILL"));
            }
            System.out.println("Producer finished.");

        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

}
