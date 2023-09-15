package AccessValidation;

import AccountCreation.AccNumbers;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;


public class SignUp extends ServerCommunication {
    private final String password;
    private final String username;
    private final JSONArray arguments;

    public SignUp(UUID clientHandler, JSONArray arguments) throws SQLException {
        super(clientHandler);
        this.username = arguments.get(0).toString();
        this.password = arguments.get(1).toString();
        this.arguments = arguments;
    }


    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        String hashPassword;
        String message;
        String result;
        response = new JSONObject();
        try {
            String username = arguments.get(0).toString();
            String usernameConfirmation = arguments.get(1).toString();

            String password = arguments.get(2).toString();
            String passwordConfirmation = arguments.get(3).toString();

            String id = arguments.get(4).toString();
            System.out.println(id);

            String emailAddress = arguments.get(5).toString();

            String residentialAddress = arguments.get(6).toString();

            String cellNumber = arguments.get(7).toString();
            String firstname = arguments.get(8).toString();
            String surname = arguments.get(9).toString();

            if (id.matches("[0-9]+")) {
                LocalDate date = LocalDate.now();
                int todayYear = date.getYear();
                int idYear;
                if(id.startsWith("9")) {
                    idYear = Integer.parseInt("19" + id.substring(0, 2));
                }
                else {
                    idYear = Integer.parseInt("20" + id.substring(0, 2));
                }
                System.out.println(idYear);

                if ((todayYear - idYear) >= 18) {
                    if (username.equals(usernameConfirmation) && password.equals(passwordConfirmation) && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                        hashPassword = pbkdf2c.generatePasswordHash(password);
                        dao.insertUserNameAndPassword(username, hashPassword);
                        int accountHolderID = dao.getPrimaryID(username);
                        String accNumber = AccNumbers.generateAccountNumber();
                        dao.addAccountNumber(accountHolderID, accNumber);
                        dao.accountTransactionsTracker(accNumber);
                        dao.addUserDetails(firstname,surname,id,emailAddress,residentialAddress,cellNumber,accountHolderID);
                        result = "OK";
                        message = "signup successful , welcome to JAVA BANK " + username;

                        dao.closeConnection();
                    } else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                        result = "ERROR";
                        message = "Invalid password";
                    } else {
                        result = "ERROR";
                        message = "Signup failed, Password and/or username do not match";
                    }
                    response.put("result", result);
                    response.put("message", message);
                }
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
