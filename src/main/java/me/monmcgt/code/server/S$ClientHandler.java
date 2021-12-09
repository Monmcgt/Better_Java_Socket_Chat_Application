package me.monmcgt.code.server;

import me.monmcgt.code.enums.MessageType;
import me.monmcgt.code.storage.Data;
import me.monmcgt.code.storage.Storage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class S$ClientHandler implements Runnable, Serializable {
    protected Socket socket;

    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    protected String username;

    public S$ClientHandler(Socket socket) {
        this.socket = socket;
        this.init();
        Storage.clients.add(this);
    }

    @Override
    public void run() {
        try {
            while (this.socket.isConnected()) {
                Data d = (Data) this.in.readObject();
                if (d.getType() == MessageType.JOIN) {
                    this.username = d.getAuthor();
                    System.out.println("[SERVER] " + this.username + " has joined the server.");
                } else {
                    this.sendData(d);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            if (e.getMessage().equals("Connection reset")) {
                System.out.println("[SERVER] " + this.username + " has disconnected.");
                Storage.clients.remove(this);
            } else {
                e.printStackTrace();
            }
        }
    }

    protected void init() {
        try {
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
            this.in = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void sendData(Data d) {
        for (S$ClientHandler c : Storage.clients) {
            if (c.username.equals(d.getAuthor())) continue;
            c.printToScreen(d);
        }
    }

    public void printToScreen(Data data) {
        try {
            this.out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }
}
