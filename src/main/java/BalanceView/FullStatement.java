package BalanceView;

import AccessValidation.ServerCommunication;
import Servers.ServerSocket.ClientStuff;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class FullStatement extends ServerCommunication {
    private int days;
    public FullStatement(ClientStuff clientStuff, JSONArray array) throws SQLException {
        super(clientStuff, array);
        this.accountNumber = clientHandler.getAccountNumber();
        this.currentBalance = dao.getCurrentBalanceAccNum(accountNumber);
        this.days = Integer.parseInt(array.get(0).toString());
    }

    @Override
    public JSONObject getResponse() {
        return null;
    }

    @Override
    public void execute() {
        response = new JSONObject();
        JSONObject accountInfo = new JSONObject();

        LocalDate date = LocalDate.now();
        LocalDate futureDate = LocalDate.now().plusDays(days);
//        try {
//            accountInfo.put("Account Number", accountNumber);
//            accountInfo.put("Current Balance", currentBalance);
//            accountInfo.put("Savings Balance", dao.getSavingsBalanceAccNum(accountNumber));
//            response.put("message", "OK");
//            response.put("Account Information", accountInfo);
//            dao.closeConnection();
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }
        dao.getAccStatement(futureDate,accountNumber);
    }
}
