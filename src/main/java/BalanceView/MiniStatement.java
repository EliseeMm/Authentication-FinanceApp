package BalanceView;

import AccessValidation.ServerCommunication;
import Servers.ClientHandler;
import org.json.JSONObject;

import java.sql.SQLException;

public class MiniStatement extends ServerCommunication {
    public MiniStatement(ClientHandler clientHandler) throws SQLException {
        super(clientHandler);
        this.accountNumber = clientHandler.getAccountNumber();
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

            response.put("message", "OK");
            response.put("Account Information", accountInfo);
            dao.closeConnection();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
