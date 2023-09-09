package ClientSide;

import ClientSide.JSONRequestBuilders.RequestJsonCreation;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;

public class WebClient {

    private UUID uuid = null;

    private void sendPostRequest(HttpURLConnection connection) throws IOException {
        connection.setRequestMethod("POST");
        JSONObject command = RequestJsonCreation.createRequest(uuid);
        connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(String.valueOf(command));
        out.flush();
        out.close();

        connection.getHeaderField("Content-Type");
        connection.setReadTimeout(5000);
//        int status = connection.getResponseCode();
    }

    private StringBuffer readResponse(HttpURLConnection connection) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        StringBuffer content = new StringBuffer();

        while ((input = in.readLine()) != null){
            content.append(input);
        }
        System.out.println(content);
        JSONObject response = new JSONObject(content.toString());
        if(response.has("UUID")) {
            System.out.println("here");
            uuid = UUID.fromString(response.getString("UUID"));
            System.out.println(uuid);
        }
        System.out.println(response.toString(4));
        return content;
    }

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://localhost:5050/banking");

        WebClient webClient = new WebClient();
        while (true) {
            HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            webClient.sendPostRequest(connection);
            webClient.readResponse(connection);

        }

    }
}
