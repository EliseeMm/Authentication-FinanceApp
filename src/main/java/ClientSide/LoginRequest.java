package ClientSide;

import org.json.JSONObject;

import java.util.Scanner;

public class LoginRequest implements Requests{
    private final JSONObject request = new JSONObject();
    private final Scanner scanner = new Scanner(System.in);

    public JSONObject getRequest(){
        System.out.println("Enter Username");
        String userName = scanner.nextLine();

        System.out.println("Enter Password");
        String password = scanner.nextLine();

        String[] arguments = {userName,password};
        request.put("arguments",arguments);

        return request;
    }
}
