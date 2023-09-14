package Servers.ServerSocket.ServerCommands;

import org.json.JSONObject;

import java.sql.SQLException;

public class UnblockUser extends ServerCommands{
    private final String username;
    protected UnblockUser(String username) throws SQLException {
        this.username = username;
    }
    @Override
    public JSONObject execute() {
        dao.blockOrUnblock(true,username);
        response.put("message",username +": has been unblocked");
        return response;
    }
}
