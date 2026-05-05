package ru.steelDeezer.service;

import ru.steelDeezer.model.Message;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    public static AtomicInteger atomicCounter = new AtomicInteger(0);
    private final String name;
    private  final Queue<Message> buffer;

    public Consumer (String name, Queue<Message> buffer){
        this.name = name;
        this.buffer = buffer;
        atomicCounter.getAndIncrement();
    }

    public void run(){
        try {
            while (true){
                synchronized (buffer){
                    while (buffer.isEmpty()){
                        buffer.wait();
                    }
                    Message msg = buffer.poll();
                    if(msg.getText().equals("POISON_PILL")){
                        System.out.println(name + " received poison pill, stopping...");
                        break;
                    }

                    System.out.println(name + " consumed" + msg);
                    buffer.notifyAll();
                }
                Thread.sleep(200);

            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public String getName() {
        return name;
    }

    public static AtomicInteger getAtomicCounter() {
        return atomicCounter;
    }

}

