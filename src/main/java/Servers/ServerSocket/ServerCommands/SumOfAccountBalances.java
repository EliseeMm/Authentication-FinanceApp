package Servers.ServerSocket.ServerCommands;

import org.json.JSONObject;

import java.sql.SQLException;

public class SumOfAccountBalances extends ServerCommands{
    protected SumOfAccountBalances() throws SQLException {
    }

    @Override
    public JSONObject execute() {
        try{
        response.put("Result",dao.getTotalUsersAmount());
        dao.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
