package Servers;

import DatabaseAccess.DatabaseAccessCode;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class MainServer implements Runnable {
    private ServerSocket serverSocket;

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


    public static void main(String[] args) throws IOException, SQLException {
        DatabaseAccessCode dao = new DatabaseAccessCode("passwords.db");
        dao.initializeDataBase();
        dao.accountNumbers();
        ServerSocket serverSocket1 = new ServerSocket(5000);
        MainServer mainServer = new MainServer(serverSocket1);
        mainServer.run();
    }

}
