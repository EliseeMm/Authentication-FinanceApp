package Servers.ServerSocket.ServerCommands;

import org.json.JSONObject;

import java.sql.SQLException;

public class BlockUser extends ServerCommands {
    private final String username;

    protected BlockUser(String username) throws SQLException {
        this.username = username;
    }

    @Override
    public JSONObject execute() {
        try {
            dao.blockOrUnblock(false, username);
            response.put("message", username + ": has been blocked");
            dao.closeConnection();
            return response;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
