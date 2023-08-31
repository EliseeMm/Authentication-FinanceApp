package ClientSide;

import org.json.JSONObject;

import java.util.Scanner;

public class SimpleRequests implements Requests{
    private final Scanner scanner = new Scanner(System.in);
    private final JSONObject request = new JSONObject();
    @Override
    public JSONObject getRequest() {
        System.out.println("Enter Amount");
        String amount = scanner.nextLine();
        String[] arguments = {amount};
        request.put("arguments",arguments);
        return request;
    }
}
