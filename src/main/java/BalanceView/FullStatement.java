package BalanceView;

import AccessValidation.ServerCommunication;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FullStatement extends ServerCommunication {
    private final int days;

    public FullStatement(String accountNumber, JSONArray array) throws SQLException {
        super(accountNumber, array);
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

        try {
            ResultSet rs = dao.getAccStatement(futureDate, accountNumber);
            while (rs.next()) {
                JSONObject data = new JSONObject();
                int transactionID = rs.getInt("transactionID");
                String transactionDate = rs.getString("TransactionDate");
                String reference = rs.getString("Reference");
                int amount = rs.getInt("Amount");
                int fee = rs.getInt("Fee");
                int balance = rs.getInt("Balance");

                data.put("Date", transactionDate);
                data.put("Reference", reference);
                data.put("Amount", amount);
                data.put("Fee", fee);
                data.put("Balance", balance);

                response.put("result", "OK");
                response.put(String.valueOf(transactionID), data);
                System.out.println(date);
            }
            dao.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
