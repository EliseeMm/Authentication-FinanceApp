package Servers.ServerSocket.ServerCommands;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrieveAccountHolderDetails extends ServerCommands {
    private final String accountNumber;

    protected RetrieveAccountHolderDetails(String accountNumber) throws SQLException {
        this.accountNumber = accountNumber;
    }

    @Override
    public JSONObject execute() {
        try {
            int id = dao.getAccountUserId(accountNumber);
            ResultSet rs = dao.getUserDetails(id);

            while (rs.next()) {
                response.put("Name", rs.getString("name"));
                response.put("Surname", rs.getString("surname"));
                response.put("ID number", rs.getString("IDNumber"));
                response.put("Email", rs.getString("email"));
                response.put("Cell Phone", rs.getString("phone"));
                response.put("Address", rs.getString("address"));

            }
            dao.closeConnection();
            return response;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
