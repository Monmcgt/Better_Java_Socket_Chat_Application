package me.monmcgt.code.server;

import java.io.IOException;
import java.net.ServerSocket;

public class S$runServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1337);

            S$Server server = new S$Server(serverSocket);
            new Thread(server).start();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
