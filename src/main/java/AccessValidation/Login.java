package AccessValidation;

import BalanceView.MiniStatement;
import Servers.ClientHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class Login extends ServerCommunication {
    private final String password;
    private final String username;
    public Login(ClientHandler clientHandler, JSONArray arguments) throws SQLException {
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
        String result;
        response = new JSONObject();
        String hashSaved = dao.getHashPassword(username);
        try {
            if (pbkdf2c.doHashPasswordsMatch(hashSaved, password)) {
                result = "Login Successful";
                this.clientHandler.setAccountNumber(dao.getAccountNumber(username));
            } else {
                result = "Login failed";
            }
            response.put("message", result);
            response.put("Balance",dao.getCurrentBalanceAccNum(clientHandler.getAccountNumber()));
            dao.closeConnection();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
