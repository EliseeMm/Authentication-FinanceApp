package Servers.ServerSocket.ServerCommands;

import org.json.JSONObject;

import java.sql.SQLException;

public class SumOfAccountBalances extends ServerCommands{
    protected SumOfAccountBalances() throws SQLException {
    }

    @Override
    public JSONObject execute() {

        response.put("Result",dao.getTotalUsersAmount());
        return response;
    }
}
