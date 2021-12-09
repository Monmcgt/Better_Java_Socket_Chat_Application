package me.monmcgt.code.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class C$runClient {
    private static String username;

    public static void main(String[] args) {
        try {
            System.out.print("Enter username: ");
            username = new Scanner(System.in).nextLine();

            Socket socket = new Socket("localhost", 1337);

            C$Client client = new C$Client(socket, username);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
