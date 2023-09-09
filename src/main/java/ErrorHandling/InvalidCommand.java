package ErrorHandling;

import AccessValidation.ServerCommunication;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.UUID;

public class InvalidCommand extends ServerCommunication {
    private JSONObject response;
    public InvalidCommand(UUID uuid) throws SQLException {
        super(uuid);
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
