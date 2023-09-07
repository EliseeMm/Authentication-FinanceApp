package ClientSide.JSONRequestBuilders;

import BalanceView.FullStatement;
import org.json.JSONObject;

import java.util.Scanner;

public class RequestJsonCreation {
    private final static Scanner scanner = new Scanner(System.in);

    public static JSONObject createRequest(){
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
            case "savings deposit","savings withdrawal","cash withdrawal","cash deposit" -> {
                requestsArguments = new SimpleRequests().getRequest();
            }
            case "mini statement"-> {
                requestsArguments = new MiniStatementRequest().getRequest();
            }
            case "full statement"-> {
                requestsArguments = new FullStatementRequest().getRequest();
            }
            case "sign out"-> {
                requestsArguments = new SignOutRequest().getRequest();
            }

        }
        request.put("Request",command);
        request.put("Arguments",requestsArguments.get("arguments"));
        return request;
    }
}
