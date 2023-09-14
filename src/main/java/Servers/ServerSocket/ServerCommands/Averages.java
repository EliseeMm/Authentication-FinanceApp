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
            response.put("Average", average);
            ResultSet above = dao.aboveOrBelowAverage(">", average);

            while (above.next()) {
                String accountNumber = above.getString("accountNumber");
                int balance = above.getInt("balance");
                aboveAverage.put("Account",accountNumber);
                aboveAverage.put("Balance",balance);

            }
            response.put("Above average",aboveAverage);

            ResultSet below = dao.aboveOrBelowAverage("<", average);

            while (below.next()) {
                String ll = above.getString("accountNumber");
                int a = above.getInt("balance");
                System.out.println(ll);
                System.out.println(a);
                belowAverage.put("Account",ll);
                belowAverage.put("Balance",a);

            }
            response.put("Below average",belowAverage);
            dao.closeConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
