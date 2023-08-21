import java.util.Scanner;

public class SignUp {
    private String password;

    private String username;
    private final Scanner scanner = new Scanner(System.in);



    public void setLoginDetails() {

        this.username = confirmUserName();
        this.password = confirmLoginPassword();

    }

    private String confirmUserName(){
        String userName;
        String userNameConfirmation;

        do {
            System.out.println("Enter user name:");
            userName = getScanner();
            System.out.println("confirm user name:");
            userNameConfirmation = getScanner();
            if(doesNotMatch(userName, userNameConfirmation)){
                System.out.println("user names don't match\n");
            }
        } while (!userName.equals(userNameConfirmation));

        System.out.println("\nUser name set\n");

        return userName;
    }

    private String confirmLoginPassword(){
        String passwordAttempt;
        String passwordConfirmation;
        do {
            System.out.println("Enter user password:");
            passwordAttempt = getScanner();
            System.out.println("confirm user password:");
            passwordConfirmation = getScanner();
            if(doesNotMatch(passwordAttempt, passwordConfirmation)){
                System.out.println("Passwords don't match\n");
            }
        } while (!passwordAttempt.equals(passwordConfirmation));

        System.out.println("\nPasswords set\n");

        return passwordAttempt;
    }

    private String getScanner(){
        return scanner.nextLine();
    }

    private boolean doesNotMatch(String a,String b){
        return !a.equals(b);
    }

    public static void main(String[] args) {
        SignUp signUp = new SignUp();
        signUp.setLoginDetails();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
