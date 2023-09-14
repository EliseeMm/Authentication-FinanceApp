package Servers.ServerSocket.ServerCommands;

import org.json.JSONObject;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListAccountHolders extends ServerCommands{
    protected ListAccountHolders() throws SQLException {
    }

    @Override
    public JSONObject execute() {
        ResultSet rs = dao.getAccountHolders();

        try {
            while (rs.next()) {
                JSONObject accHolder = new JSONObject();
                int id = rs.getInt("accountHolderID");
                String username = rs.getString("username");
                String accountNumber = rs.getString("accountNumber");
                String accBalance = rs.getString("balance");
                String savingsBalance = rs.getString("savingsBalance");
                accHolder.put("Username",username);
                accHolder.put("Account Number",accountNumber);
                accHolder.put("Account Balance",accBalance);
                accHolder.put("Savings Balance",savingsBalance);
                response.put(String.valueOf(id),accHolder);
            }
            return response;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
