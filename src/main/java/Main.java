import AccessValidation.Login;
import AccessValidation.SignUp;
import AccountCreation.AccNumbers;
import DatabaseAccess.DatabaseAccessCode;
import PasswordManagement.PBKDF2;
import Transact.SendMoney;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.sql.SQLOutput;
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
                System.out.println("Enter acc number:");
                String acc = scanner.nextLine();

                if(dao.validAccountNumber(acc)) {
                    System.out.println("Amount:");
                    int amount = scanner.nextInt();

                    int userCurrentBalance = dao.getCurrentBalance(username);

                    SendMoney sendMoney = new SendMoney(userCurrentBalance, amount);
                    sendMoney.updateBalance();

                    int updatedBalance = sendMoney.getCurrentBalance();


                    String accountNumber = dao.getAccountNumber(username);


                    dao.decreaseSenderBalance(updatedBalance, accountNumber); // updating senders balance


                    // updating recipient balance
                    int currentBalanceOfRecipient = dao.getCurrentBalanceAccNum(acc);
                    dao.increaseRecipientBalance(currentBalanceOfRecipient + amount, acc);

                    System.out.println("Logged in");
                }
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
