package AccessValidation;

import AccountCreation.AccNumbers;
import Servers.ClientHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;


public class SignUp extends ServerCommunication {
    private final String password;
    private final String username;
    public SignUp(ClientHandler clientHandler, JSONArray arguments) throws SQLException {
        super(clientHandler);
        this.username = arguments.get(0).toString();
        this.password = arguments.get(1).toString();
    }


    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        String hashPassword;
        response = new JSONObject();
        try {
            hashPassword = pbkdf2c.generatePasswordHash(password);
            dao.insertUserNameAndPassword(username, hashPassword);
            String accNumber = AccNumbers.generateAccountNumber();
            dao.addAccountNumber(dao.getPrimaryID(username), accNumber);
            dao.accountTransactionsTracker(accNumber);
            response.put("message", "signup successful");
            dao.closeConnection();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
