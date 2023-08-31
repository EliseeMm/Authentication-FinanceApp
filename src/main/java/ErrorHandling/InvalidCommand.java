package ErrorHandling;

import AccessValidation.ServerCommunication;
import Servers.ClientHandler;
import org.json.JSONObject;

import java.sql.SQLException;

public class InvalidCommand extends ServerCommunication {
    private JSONObject response;
    public InvalidCommand(ClientHandler clientHandler) throws SQLException {
        super(clientHandler);
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        response = new JSONObject();
        response.put("message", "Invalid Command");
    }
}
