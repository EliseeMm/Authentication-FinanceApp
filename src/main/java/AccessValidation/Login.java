package AccessValidation;

import Servers.ServerSocket.ClientHandler;
import Servers.ServerSocket.ClientStuff;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class Login extends ServerCommunication {
    private final String password;
    private final String username;
    public Login(ClientStuff clientHandler, JSONArray arguments) throws SQLException {
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
        String message;
        response = new JSONObject();
        String hashSaved = dao.getHashPassword(username);
        try {
            if (pbkdf2c.doHashPasswordsMatch(hashSaved, password)) {
                result = "OK";
                clientHandler.setAccountNumber(dao.getAccountNumber(username));
                clientHandler.setUsername(username);
                message = "Login Successful,Welcome "+clientHandler.getUsername();
                LoggedInUsers.addUser(clientHandler);
                System.out.println(LoggedInUsers.getUsers());
            } else {
                result = "ERROR";
                message = "Login failed";

            }
            response.put("result",result);
            response.put("message", message);
            response.put("Balance",dao.getCurrentBalanceAccNum(clientHandler.getAccountNumber()));
            dao.closeConnection();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
