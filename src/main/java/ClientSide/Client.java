package ClientSide;

import ClientSide.JSONRequestBuilders.RequestJsonCreation;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class Client {
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    private final Socket socket;
    private final Scanner scanner = new Scanner(System.in);
    private UUID uuid = null;

    public Client(Socket socket){
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.socket = socket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readMessage(){
        try {
            String message = bufferedReader.readLine();

            if(message != null) {
                JSONObject js = new JSONObject(message);
                if(js.has("UUID")) {
                    System.out.println("here");
                    uuid = UUID.fromString(js.getString("UUID"));
                    System.out.println(uuid);
                }
                System.out.println(js.toString(4));
                System.out.println(uuid);
                JSONObject command = RequestJsonCreation.createRequest(uuid);




                bufferedWriter.write(command.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();


            }
            else {
                closeEverything(socket,bufferedWriter,bufferedReader);
            }

        } catch (IOException e) {
            System.out.println("Server closed");
            System.exit(0);
        }
    }
    public void closeEverything(Socket socket,BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        try {
            socket.close();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",5000);
        Client client = new Client(socket);
        while (socket.isConnected()){
            client.readMessage();
        }
    }
}
