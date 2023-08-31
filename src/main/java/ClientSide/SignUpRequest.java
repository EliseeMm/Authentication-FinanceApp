package ClientSide;

import org.json.JSONObject;

import java.util.Scanner;

public class SignUpRequest implements Requests {

    private final JSONObject request = new JSONObject();
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public JSONObject getRequest() {

        String userName = getUsername();
        String password = getPassword();
        String[] arguments = {userName,password};
        request.put("arguments",arguments);
        return request;
    }

    public String getUsername(){
        String userName;
        String userNameConfirmation;

        do {
            System.out.println("Enter user name:");
            userName = getScanner();
            System.out.println("confirm user name:");
            userNameConfirmation = getScanner();
            if (doesNotMatch(userName, userNameConfirmation)) {
                System.out.println("user names don't match\n");
            }
        } while (!userName.equals(userNameConfirmation));
        return userName;
    }
    private String getPassword() {
        String passwordAttempt;
        String passwordConfirmation;
        do {
            System.out.println("Enter user password:");
            passwordAttempt = getScanner();
            System.out.println("confirm user password:");
            passwordConfirmation = getScanner();
            if (doesNotMatch(passwordAttempt, passwordConfirmation)) {
                System.out.println("Passwords don't match\n");
            }
        } while (!passwordAttempt.equals(passwordConfirmation));

        System.out.println("\nPasswords set\n");

        return passwordAttempt;
    }
    private String getScanner() {
        return scanner.nextLine();
    }
    private boolean doesNotMatch(String a, String b) {
        return !a.equals(b);
    }
}
