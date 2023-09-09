package Transact;

import AccessValidation.ServerCommunication;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class CashDeposit extends ServerCommunication {
    public CashDeposit(String accountNumber, JSONArray array) throws SQLException {
        super(accountNumber,array);
        this.amount = Integer.parseInt(array.get(0).toString());
        this.currentBalance = dao.getCurrentBalanceAccNum(accountNumber);
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        try {
            response = new JSONObject();
            currentBalance += amount;
            dao.updateBalance(currentBalance, accountNumber);
            dao.updateTransactionTracker(accountNumber, LocalDate.now(), "Cash Deposit", amount, currentBalance);
            result = "OK";
            message = "Successful Deposit";

            response.put("result",result);
            response.put("message",message);
            response.put("UUID",uuid);
            response.put("Balance",currentBalance);
            dao.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
