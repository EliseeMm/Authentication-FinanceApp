import AccessValidation.Login;
import AccessValidation.SignUp;
import AccountCreation.AccNumbers;
import DatabaseAccess.DatabaseAccessCode;
import PasswordManagement.PBKDF2;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {

        System.out.println("1 to login\n2 to signup");

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        DatabaseAccessCode dao = new DatabaseAccessCode("passwords.db");
        PBKDF2 pbkdf2c = new PBKDF2();

        if(input.equalsIgnoreCase("1")){
            Login login = new Login();
            String username = login.getUsername();
            String password = login.getPassword();

            String hashSaved = dao.getHashPassword(username);
            if(pbkdf2c.doHashPasswordsMatch(hashSaved,password)){
                Transactions transactions = new Transactions(dao.getOpeningBalance(username));
                System.out.println("Logged in");
            }


        } else if (input.equalsIgnoreCase("2")) {
            SignUp signUp = new SignUp();

            String userName = signUp.getUsername();
            String password = signUp.getPassword();

            String hashPassword = pbkdf2c.generatePasswordHash(password);

            dao.insertUserNameAndPassword(userName,hashPassword);

            dao.addAccountNumber(dao.getPrimaryID(userName), AccNumbers.generateAccountNumber());
        }









    }
}
