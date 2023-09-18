package BalanceView;

import AccessValidation.ServerCommunication;
import org.json.JSONObject;

import java.sql.SQLException;

public class MiniStatement extends ServerCommunication {
    public MiniStatement(String accountNumber) throws SQLException {
        super(accountNumber);
        this.currentBalance = dao.getCurrentBalanceAccNum(accountNumber);
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        response = new JSONObject();
        JSONObject accountInfo = new JSONObject();

        try {
            accountInfo.put("Account Number", accountNumber);
            accountInfo.put("Current Balance", currentBalance);
            accountInfo.put("Savings Balance", dao.getSavingsBalanceAccNum(accountNumber));
            response.put("result", "OK");
            response.put("UUID", uuid);
            response.put("Account Information", accountInfo);
            dao.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
