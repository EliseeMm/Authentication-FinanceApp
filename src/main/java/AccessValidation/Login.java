package AccessValidation;

import Servers.ServerSocket.ClientHandler;
import Servers.ServerSocket.ClientStuff;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

public class Login extends ServerCommunication {
    private final String password;
    private final String username;
    private UUID uuid ;
    public Login(UUID uuid, JSONArray arguments) throws SQLException {
        super(uuid);
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
            if (hashSaved == null){
                result = "ERROR";
                message = "Login failed";
            }
            else if (pbkdf2c.doHashPasswordsMatch(hashSaved, password) && isAccountActive(username)) {
                result = "OK";
                message = "Login Successful,Welcome "+ username;
                accountNumber = dao.getAccountNumber(username);
                uuid = generateUUID();
                LoggedInUsers.addUser(uuid,accountNumber);
            } else {
                result = "ERROR";
                message = "Account is no longer active";
            }
            response.put("UUID",uuid);
            response.put("result",result);
            response.put("message", message);
            response.put("Balance",dao.getCurrentBalanceAccNum(accountNumber));
            dao.closeConnection();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private UUID generateUUID(){
        do{
            uuid = new UUID(new Random().nextLong(),new Random().nextLong());
        }
        while (LoggedInUsers.getUsers().containsKey(uuid.toString()));
        return uuid;
    }

}
