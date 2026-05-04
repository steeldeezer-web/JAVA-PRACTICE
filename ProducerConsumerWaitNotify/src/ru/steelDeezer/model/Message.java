package ru.steelDeezer.model;

public class Message {
    private final String text;

    public Message(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
