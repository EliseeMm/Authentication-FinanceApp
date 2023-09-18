package Servers.ServerSocket;

import Servers.ServerSocket.ServerCommands.ServerCommands;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

public class MainServer implements Runnable {
    private final ServerSocket serverSocket;
    private Scanner scanner;

    public MainServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        serverAdminCommands(serverSocket);
        try {
            System.out.println("Server up and running");
            while (!serverSocket.isClosed()) {

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

    private void serverAdminCommands(ServerSocket serverSocket) {
        new Thread(() -> {
            scanner = new Scanner(System.in);
            while (!serverSocket.isClosed()) {
                // Read the next line of input from the keyboard
                if (scanner.hasNextLine()) {
                    String serverCommand = scanner.nextLine();
                    ServerCommands command;
                    try {
                        command = ServerCommands.returnCommand(serverCommand);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(command.execute().toString(4));

                }
            }
        }).start();
    }


}
