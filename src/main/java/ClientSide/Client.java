package ClientSide;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    private final Scanner scanner = new Scanner(System.in);

    public Client(Socket socket){
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readMessage(){
        try {
            String message = bufferedReader.readLine();
            System.out.println(message);

            JSONObject command = createRequest();

            bufferedWriter.write(command.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject createRequest(){
        String command = scanner.nextLine().toLowerCase();
        JSONObject request = new JSONObject();
        String requestString = "";
        JSONObject requestsArguments = new JSONObject();

        switch (command) {
            case "login" -> {
                requestsArguments = new LoginRequest().getRequest();
            }
            case "payment" -> {
                requestsArguments = new SendMoneyRequest().getRequest();
            }
            case "signup" -> {
                requestsArguments = new SignUpRequest().getRequest();
            }
            case "savings deposit","savings withdrawal","cash withdrawal" -> {
                requestsArguments = new SimpleRequests().getRequest();
            }
        }
        request.put("Request",command);
        request.put("Arguments",requestsArguments.get("arguments"));
        return request;
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",5000);
        Client client = new Client(socket);
        while (socket.isConnected()){
            client.readMessage();
        }
    }
}
