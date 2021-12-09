package me.monmcgt.code.server;

import me.monmcgt.code.client.C$Client;
import me.monmcgt.code.enums.MessageType;
import me.monmcgt.code.storage.Data;

import java.io.IOException;
import java.net.Socket;

public class S$ClientSimulator extends C$Client {
    public S$ClientSimulator(Socket socket) {
        super(socket, "administrator");
    }

    @Override
    public void start() {
        new Thread(this).start();
    }

    public void sendMessage(String message) {
        try {
            this.out.writeObject(new Data(MessageType.MESSAGE, this.username, message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return this.username;
    }
}
