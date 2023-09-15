package Transact;

import AccessValidation.ServerCommunication;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class SavingsDeposit extends ServerCommunication {
    public SavingsDeposit(String accountNumber, JSONArray array) throws SQLException {
        super(accountNumber, array);
        this.currentBalance = dao.getCurrentBalanceAccNum(accountNumber);
        this.amount = Integer.parseInt(array.get(0).toString());
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
                currentBalance -= amount;
                int CurrentSavings = dao.getSavingsBalanceAccNum(accountNumber);
                dao.updateSavings(CurrentSavings + amount, accountNumber);
                dao.updateBalance(currentBalance, accountNumber);
                dao.updateTransactionTracker(accountNumber, LocalDate.now(),"Savings Deposit",-amount,0,currentBalance);
                result = "OK";
                message = "Deposit Successful";
            }
            else{
                result = "ERROR";
                message = "Deposit Failed";
            }
            response.put("result",result);
            response.put("UUID",uuid);
            response.put("message", message);
            response.put("Balance",currentBalance);
            dao.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
