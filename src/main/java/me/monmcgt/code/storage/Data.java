package me.monmcgt.code.storage;

import me.monmcgt.code.enums.MessageType;

import java.io.Serializable;

public class Data implements Serializable {
    private MessageType type;
    private String author;
    private String message;

    public Data(MessageType type, String author, String message) {
        this.type = type;
        this.author = author;
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }
}
