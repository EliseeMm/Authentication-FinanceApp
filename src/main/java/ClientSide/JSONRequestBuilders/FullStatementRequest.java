package ClientSide.JSONRequestBuilders;

import org.json.JSONObject;

import java.util.Scanner;

public class FullStatementRequest implements Requests {
    private final JSONObject request = new JSONObject();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public JSONObject getRequest() {
        System.out.println("30,60 or 90 days?");
        String dates = scanner.nextLine();

        String[] arguments = {dates};
        request.put("arguments", arguments);

        return request;
    }
}
