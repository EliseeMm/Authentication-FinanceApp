package ClientSide;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebClient {

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:5050/banking");
        HttpURLConnection connection =
    }
}
