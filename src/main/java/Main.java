//import AccessValidation.Login;
//import AccessValidation.SignUp;
//import AccountCreation.AccNumbers;
//import DatabaseAccess.DatabaseAccessCode;
//import PasswordManagement.PBKDF2;
//import Transact.SendMoney;
//
//import java.security.NoSuchAlgorithmException;
//import java.security.spec.InvalidKeySpecException;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.Scanner;
//
//public class Main {
//
//    private static final Scanner scanner = new Scanner(System.in);
//    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
//
//        System.out.println("1 to login\n2 to signup");
//        String input = scanner.nextLine();
//        DatabaseAccessCode dao = new DatabaseAccessCode("passwords.db");
//        PBKDF2 pbkdf2c = new PBKDF2();
//
//        if (input.equalsIgnoreCase("1")) {
//            Login login = new Login();
//            String username = login.getUsername();
//            String password = login.getPassword();
//
//            String hashSaved = dao.getHashPassword(username);
//            if (pbkdf2c.doHashPasswordsMatch(hashSaved, password)) {
//
//                String accountToSendTo = getAccNumber();
//                int amountToSend = getAmount();
//                String reference = getReference();
//
//                String accountNumber = dao.getAccountNumber(username);
//
//                if (dao.validAccountNumber(accountToSendTo) && !accountNumber.equals(accountToSendTo)) {
//
//                    int userCurrentBalance = dao.getCurrentBalance(username);
//
//                    SendMoney sendMoney = new SendMoney(userCurrentBalance, amountToSend);
//
//                    if (sendMoney.updateBalance()) {
//                        int updatedBalance = sendMoney.getCurrentBalance();
//
//
//                        dao.decreaseSenderBalance(updatedBalance, accountNumber); // updating senders balance
//                        LocalDate date = LocalDate.now();
//                        dao.updateTransactionTracker(accountNumber, date, reference, -amountToSend, dao.getCurrentBalance(username));
//
//                        // updating recipient balance
//                        int currentBalanceOfRecipient = dao.getCurrentBalanceAccNum(accountToSendTo);
//                        dao.increaseRecipientBalance(currentBalanceOfRecipient + amountToSend, accountToSendTo);
//                        dao.updateTransactionTracker(accountToSendTo, date, reference, amountToSend, dao.getCurrentBalanceAccNum(accountToSendTo));
//                        System.out.println("Logged in");
//                    }
//                }
//            }
//
//
//        } else if (input.equalsIgnoreCase("2")) {
//            SignUp signUp = new SignUp();
//
//            String userName = signUp.getUsername();
//            String password = signUp.getPassword();
//
//            String hashPassword = pbkdf2c.generatePasswordHash(password);
//
//            dao.insertUserNameAndPassword(userName, hashPassword);
//
//            String accNumber = AccNumbers.generateAccountNumber();
//            dao.addAccountNumber(dao.getPrimaryID(userName), accNumber);
//            dao.accountTransactionsTracker(accNumber);
//        }
//
//
//    }
//
//    private static String getAccNumber(){
//        System.out.println("Enter recipient account number:");
//        return scanner.nextLine();
//    }
//
//    private static int getAmount(){
//        System.out.println("Payment amount:");
//        int amount = scanner.nextInt();
//        scanner.nextLine();
//        return amount;
//    }
//    private static String getReference(){
//        System.out.println("Payment Reference:");
//        return scanner.nextLine();
//    }
//}
