package Servers.ServerSocket;

import Servers.ServerSocket.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer implements Runnable {
    private final ServerSocket serverSocket;

    public MainServer (ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    @Override
    public void run() {
        try{
            System.out.println("Server up and running");
            while (!serverSocket.isClosed()){

                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread clientThread = new Thread(clientHandler);
                System.out.println("Client has connected");
                clientThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
