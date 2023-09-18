package Transact;

import AccessValidation.ServerCommunication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class CashWithdrawal extends ServerCommunication {

    public CashWithdrawal(String accountNumber, JSONArray array) throws SQLException {
        super(accountNumber, array);
        this.amount = Integer.parseInt(array.get(0).toString());
        this.currentBalance = dao.getCurrentBalanceAccNum(accountNumber);
        //R3 for every 100
        int FEE = 3;
        transactionFee = (amount / 100) * FEE;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        response = new JSONObject();


        try {
            if (isTransactionPossible(currentBalance)) {
                currentBalance -= (amount + transactionFee);
                dao.updateBalance(currentBalance, accountNumber);
                dao.updateTransactionTracker(accountNumber, LocalDate.now(), "Cash Withdrawal", -amount, -transactionFee, currentBalance);
                result = "OK";
                message = "Withdrawal Successful";
            } else {
                result = "ERROR";
                message = "Withdrawal Failed,Insufficient funds";
            }
            response.put("result", result);
            response.put("UUID", uuid);
            response.put("message", message);
            response.put("Balance", currentBalance);
            dao.closeConnection();
        } catch (JSONException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
