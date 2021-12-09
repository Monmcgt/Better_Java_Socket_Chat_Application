package me.monmcgt.code.client;

import me.monmcgt.code.enums.MessageType;
import me.monmcgt.code.storage.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class C$Client implements Runnable {
    protected Socket socket;

    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    protected Scanner scanner;

    protected String username;

    public C$Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;

            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());

            this.scanner = new Scanner(System.in);

            this.out.writeObject(new Data(MessageType.JOIN, this.username, ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            new Thread(this).start();
            while (this.scanner.hasNext()) {
                String message = this.scanner.nextLine();
                this.out.writeObject(new Data(MessageType.MESSAGE, this.username, message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Data data = (Data) this.in.readObject();
                String content = null;
                switch (data.getType()) {
                    case JOIN:
                        content = "[SERVER] " + data.getAuthor() + " joined the server.";
                        break;
                    case LEAVE:
                        content = "[SERVER] " + data.getAuthor() + " left the server.";
                        break;
                    case MESSAGE:
                        content = "[" + data.getAuthor() + "] " + data.getMessage();
                        break;
                }
                System.out.println(content);
            } catch (IOException | ClassNotFoundException e) {
                if (e.getMessage().equals("Connection reset")) {
                    System.out.println("[SERVER] Connection reset.");
                    System.exit(0);
                } else {
                    e.printStackTrace();
                }
            }
        }
    }
}
