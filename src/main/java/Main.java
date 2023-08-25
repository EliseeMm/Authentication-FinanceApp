import DatabaseAccess.DatabaseAccessCode;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {

        System.out.println("1 to login\n2 to signup");

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        DatabaseAccessCode dao = new DatabaseAccessCode();
        PBKDF2 pbkdf2c = new PBKDF2();

        if(input.equalsIgnoreCase("1")){
            Login login = new Login();
            String username = login.getUsername();
            String password = login.getPassword();

            String hashSaved = dao.getHashPassword(username);
            System.out.println(pbkdf2c.doHashPasswordsMatch(hashSaved,password));


        } else if (input.equalsIgnoreCase("2")) {
            SignUp signUp = new SignUp();

            String userName = signUp.getUsername();
            String password = signUp.getPassword();

            String hashPassword = pbkdf2c.generatePasswordHash(password);

            dao.insertUserNameAndPassword(userName,hashPassword);

            dao.addAccountNumber(dao.getPrimaryID(userName),AccNumbers.generateAccountNumber());
        }









    }
}
