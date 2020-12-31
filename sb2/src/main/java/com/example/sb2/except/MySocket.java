package com.example.sb2.except;

import java.io.IOException;
import java.net.ServerSocket;

public class MySocket {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        serverSocket.accept();
    }
}
