import java.util.Scanner;

public class Login {
    private String username;

    private String password;
    private static Scanner sc = new Scanner(System.in);

    public Login(){
        setUsername();
        setPassword();
    }
    public void setUsername(){
        System.out.println("Enter Username");
        this.username = sc.nextLine();
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(){
        System.out.println("Enter password");
        this.password = sc.nextLine();
    }

    public String getPassword() {
        return password;
    }
}
