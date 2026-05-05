package ru.steelDeezer;

import ru.steelDeezer.model.Message;
import ru.steelDeezer.service.Consumer;
import ru.steelDeezer.service.Producer;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<Message> buffer = new LinkedList<>();
        Consumer consumer0 = new Consumer("Consumer " + Consumer.getAtomicCounter(), buffer);
        Producer producer = new Producer(buffer, 20,(int) Consumer.getAtomicCounter().get(), 5 );

        Thread thread1 = new Thread(producer);
        Thread thread0 = new Thread(consumer0);
        thread0.start();
        thread1.start();

    }


}
