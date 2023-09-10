package AccessValidation;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.UUID;

public class SignOut extends ServerCommunication{
    public SignOut(String accountNumber) throws SQLException {
        super(accountNumber);
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        for(String uuid : LoggedInUsers.getUsers().keySet()){
            if(LoggedInUsers.getUsers().get(uuid).equals(accountNumber)){
                LoggedInUsers.removeUser(UUID.fromString(uuid));
            }
        }

        response = new JSONObject();
        response.put("result","OK");
        response.put("message", "Successfully logged out");
    }
}
