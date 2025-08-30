package com.java.chatui;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server.");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            // Thread to read messages from server
            Thread readThread = new Thread(() -> {
                try {
                    String msg;
                    while (true) {
                        msg = dis.readUTF();
                        System.out.println("Server: " + msg);
                        if (msg.equalsIgnoreCase("exit")) {
                            System.out.println("Server terminated the chat.");
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });

            readThread.start();

            // Main thread to send messages to server
            String msg;
            while (true) {
                msg = scanner.nextLine();
                dos.writeUTF(msg);
                dos.flush();
                if (msg.equalsIgnoreCase("exit")) {
                    System.out.println("You terminated the chat.");
                    break;
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
