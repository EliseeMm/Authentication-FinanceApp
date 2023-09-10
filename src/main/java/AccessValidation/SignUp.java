package AccessValidation;

import AccountCreation.AccNumbers;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
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

            if(username.equals(usernameConfirmation) && password.equals(passwordConfirmation)) {

                hashPassword = pbkdf2c.generatePasswordHash(password);
                dao.insertUserNameAndPassword(username, hashPassword);
                String accNumber = AccNumbers.generateAccountNumber();
                dao.addAccountNumber(dao.getPrimaryID(username), accNumber);
                dao.accountTransactionsTracker(accNumber);
                result = "OK";
                message = "signup successful , welcome to JAVA BANK "+username;

                dao.closeConnection();
            }else{
                result = "ERROR";
                message =  "Signup failed, Password and/or username do not match";
            }
            response.put("result",result);
            response.put("message", message);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
