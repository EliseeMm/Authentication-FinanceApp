package BalanceView;

import AccessValidation.ServerCommunication;
import Servers.ServerSocket.ClientStuff;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
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
        return response;
    }

    @Override
    public void execute() {
        response = new JSONObject();


        LocalDate date = LocalDate.now();
        LocalDate futureDate = LocalDate.now().plusDays(days);
        ResultSet rs =  dao.getAccStatement(futureDate,accountNumber);
        try {
            while (rs.next()) {
                JSONObject data = new JSONObject();
                int transactionID = rs.getInt("transactionID");
                String transactionDate = rs.getString("TransactionDate");
                String reference = rs.getString("Reference");
                int amount = rs.getInt("Amount");
                int balance = rs.getInt("Balance");

                data.put("Date",transactionDate);
                data.put("Reference", reference);
                data.put("Amount",amount);
                data.put("Balance",balance);

                response.put("result","OK");
                response.put(String.valueOf(transactionID),data);
                System.out.println(date);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
