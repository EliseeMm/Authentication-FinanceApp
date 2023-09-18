package Servers.ServerSocket.ServerCommands;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class UnblockUser extends ServerCommands {
    private final String username;

    protected UnblockUser(String username) throws SQLException {
        this.username = username;
    }

    @Override
    public JSONObject execute() {
        try {
            dao.blockOrUnblock(true, username);
            response.put("message", username + ": has been unblocked");
            return response;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
