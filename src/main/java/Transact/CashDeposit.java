package Transact;

import AccessValidation.ServerCommunication;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class CashDeposit extends ServerCommunication {

    public CashDeposit(String accountNumber, JSONArray array) throws SQLException {
        super(accountNumber, array);
        this.amount = Integer.parseInt(array.get(0).toString());
        this.currentBalance = dao.getCurrentBalanceAccNum(accountNumber);
        //R2 for every 100
        int FEE = 2;
        this.transactionFee = (amount / 100) * FEE;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        try {
            response = new JSONObject();
            currentBalance += (amount - transactionFee);
            dao.updateBalance(currentBalance, accountNumber);
            dao.updateTransactionTracker(accountNumber, LocalDate.now(), "Cash Deposit", amount, -transactionFee, currentBalance);
            result = "OK";
            message = "Successful Deposit";

            response.put("result", result);
            response.put("message", message);
            response.put("UUID", uuid);
            response.put("Balance", currentBalance);
            dao.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
