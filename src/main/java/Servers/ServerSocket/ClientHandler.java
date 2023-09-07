package Servers.ServerSocket;

import AccessValidation.ServerCommunication;
import AccessValidation.SignOut;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler implements Runnable,ClientStuff {
    private final Socket socket;
    private  BufferedReader bufferedReader; // used to read text from input stream
    private  BufferedWriter bufferedWriter; // used to write to an output stream
    private  String accountNumber;
    private String username;
    public ClientHandler(Socket socket){
        this.socket = socket; // One endpoint of a 2 way communication
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // the bufferedReader is reading from the socket input
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // bufferedWriter to socket
        } catch (IOException e) {
            closeEverything(socket,bufferedWriter,bufferedReader);
        }
    }
    @Override
    public void run() {
        String clientRequest;
        try {
            JSONObject accountAccess = new JSONObject();
            accountAccess.put("Login","To login into your account");
            accountAccess.put("Signup","To create an account");


            bufferedWriter.write(accountAccess.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            while (socket.isConnected()) {

                clientRequest = bufferedReader.readLine();
                if (clientRequest != null) {
                    JSONObject request = new JSONObject(clientRequest);
                    ServerCommunication communication = ServerCommunication.createRequest(this, request);
                    communication.execute();

                    bufferedWriter.write(communication.getResponse().toString());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if(communication instanceof SignOut){
                        closeEverything(socket,bufferedWriter,bufferedReader);
                        Thread thread = Thread.currentThread();
                        thread.interrupt();
                    }

                } else {
                    closeEverything(socket, bufferedWriter, bufferedReader);
                }
            }
            }catch (IOException | SQLException e) {
                System.out.println("Client disconnected");
                closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    /**
     * Ensures the client side socket is properly closed */
    public void closeEverything(Socket socket,BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        try {
            socket.close();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString(){
        return username +" : "+accountNumber;
    }
}
