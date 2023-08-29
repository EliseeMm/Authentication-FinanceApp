package Servers;

import AccessValidation.Login;
import AccessValidation.ServerCommunication;
import JSONParsing.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler implements Runnable{
    private final Socket socket;
    private  BufferedReader bufferedReader; // used to read text from input stream
    private  BufferedWriter bufferedWriter; // used to write to an output stream
    private  PrintWriter printWriter;
    private  String accountNumber;
    public ClientHandler(Socket socket){
        this.socket = socket; // One endpoint of a 2 way communication
        try {
            this.printWriter = new PrintWriter(socket.getOutputStream()); // prints out response from socket
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
            bufferedWriter.write("Welcome");
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
}
