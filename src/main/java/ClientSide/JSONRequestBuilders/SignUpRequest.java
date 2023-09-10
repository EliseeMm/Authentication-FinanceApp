package ClientSide.JSONRequestBuilders;

import ClientSide.JSONRequestBuilders.Requests;
import org.json.JSONObject;

import java.util.Scanner;

public class SignUpRequest implements Requests {

    private final JSONObject request = new JSONObject();
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public JSONObject getRequest() {

        System.out.println("Enter user name:");
        String userName = scanner.nextLine();
        System.out.println("confirm user name:");
        String userNameConfirmation = scanner.nextLine();

        System.out.println("Enter user password:");
        String password = getScanner();
        System.out.println("confirm user password:");
        String passwordConfirmation = getScanner();


        String[] arguments = {userName,userNameConfirmation,password,passwordConfirmation};
        request.put("arguments",arguments);
        return request;
    }


    private String getScanner() {
        return scanner.nextLine();
    }

}
