package me.monmcgt.code.server;

import me.monmcgt.code.storage.Storage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class S$Server implements Runnable {
    private ServerSocket serverSocket;

    private Scanner scanner;

    private S$ClientSimulator clientSimulator;

    public S$Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        try {
            this.clientSimulator = new S$ClientSimulator(new Socket("localhost", 1337));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.clientSimulator.start();

        while (true) {
            String command = scanner.nextLine();

            switch (command) {
                case "list":
                    for (S$ClientHandler clientHandler : Storage.clients) {
                        System.out.println(clientHandler.getUsername());
                    }
                    break;
                case "size":
                    System.out.println(Storage.clients.size());
                    break;
                case "whoami" :
                    System.out.println(this.clientSimulator.getUsername());
                    break;
                case "msg" :
                    System.out.print("Please enter the message: ");
                    String message = this.scanner.nextLine();
                    this.clientSimulator.sendMessage(message);
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
    }

    @Override
    public void run() {
        try {
            do {
                Socket s = serverSocket.accept();
                S$ClientHandler clientHandler = new S$ClientHandler(s);
                new Thread(clientHandler).start();
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
