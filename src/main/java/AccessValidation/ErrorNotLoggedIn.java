package AccessValidation;

import Servers.ServerSocket.ClientStuff;
import org.json.JSONObject;

import java.sql.SQLException;

public class ErrorNotLoggedIn extends ServerCommunication {
    public ErrorNotLoggedIn(ClientStuff clientStuff) throws SQLException {
        super(clientStuff);
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        response = new JSONObject();
        response.put("result","Error");
        response.put("message","User Not Logged In");
    }
}
