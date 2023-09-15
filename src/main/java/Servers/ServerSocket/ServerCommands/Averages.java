package Servers.ServerSocket.ServerCommands;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Averages extends ServerCommands{
    protected Averages() throws SQLException {
    }

    @Override
    public JSONObject execute() {
        JSONObject aboveAverage = new JSONObject();
        JSONObject belowAverage = new JSONObject();
        try {
            int average = dao.getAverage();
            response.put("Average Account Balance", average);
            ResultSet above = dao.aboveOrBelowAverage(">", average);

            while (above.next()) {
                JSONObject user = new JSONObject();
                int id = above.getInt("accountHolderID");
                String accountNumber = above.getString("accountNumber");
                int balance = above.getInt("balance");

                user.put("Account",accountNumber);
                user.put("Balance",balance);
                aboveAverage.put(String.valueOf(id),user);

            }
            response.put("Above Account Balance average",aboveAverage);

            ResultSet below = dao.aboveOrBelowAverage("<", average);

            while (below.next()) {
                JSONObject user = new JSONObject();
                int id = below.getInt("accountHolderID");
                String accountNumber = below.getString("accountNumber");
                int balance = below.getInt("balance");

                user.put("Account",accountNumber);
                user.put("Balance",balance);
                belowAverage.put(String.valueOf(id),user);

            }
            response.put("Below Account Balance average",belowAverage);
            dao.closeConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
