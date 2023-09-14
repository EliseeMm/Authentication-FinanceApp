package Servers.ServerSocket.ServerCommands;

import org.json.JSONObject;

import java.sql.SQLException;

public class CloseServer extends ServerCommands{
    public CloseServer() throws SQLException {
    }

    @Override
    public JSONObject execute() {
        System.exit(0);
        return response;
    }
}
