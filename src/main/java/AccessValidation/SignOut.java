package AccessValidation;

import Servers.ServerSocket.ClientStuff;
import org.json.JSONObject;

import java.sql.SQLException;

public class SignOut extends ServerCommunication{
    public SignOut(ClientStuff clientStuff) throws SQLException {
        super(clientStuff);
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        LoggedInUsers.removeUser(clientHandler);
        response = new JSONObject();
        response.put("result","OK");
        response.put("message",clientHandler.getUsername() + " Successfully logged out");
        System.out.println(LoggedInUsers.getUsers());
    }
}
