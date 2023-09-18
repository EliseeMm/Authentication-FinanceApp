package ClientSide.JSONRequestBuilders;

import org.json.JSONObject;

import java.util.Scanner;

public class SignUpRequest implements Requests {

    private final JSONObject request = new JSONObject();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public JSONObject getRequest() {
        System.out.println("First name: ");
        String firstName = scanner.nextLine();

        System.out.println("Surname: ");
        String surname = scanner.nextLine();

        System.out.println("Enter user name: ");
        String userName = scanner.nextLine();

        System.out.println("confirm user name: ");
        String userNameConfirmation = scanner.nextLine();

        System.out.println("Enter user password: ");
        String password = getScanner();

        System.out.println("confirm user password: ");
        String passwordConfirmation = getScanner();

        System.out.println("Enter Valid ID number: ");
        String idNumber = getScanner();

        System.out.println("Enter email address: ");
        String emailAddress = getScanner();

        System.out.println("Enter Residential address: ");
        String residentialAddress = getScanner();

        System.out.println("Enter Cell Number: ");
        String cellNumber = getScanner();

        String[] arguments = {userName, userNameConfirmation, password, passwordConfirmation, idNumber, emailAddress, residentialAddress, cellNumber, firstName, surname};
        request.put("arguments", arguments);
        return request;
    }


    private String getScanner() {
        return scanner.nextLine();
    }

}
