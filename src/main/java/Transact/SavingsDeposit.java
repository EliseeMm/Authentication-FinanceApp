package Transact;

import AccessValidation.ServerCommunication;
import Servers.ServerSocket.ClientHandler;
import Servers.ServerSocket.ClientStuff;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class SavingsDeposit extends ServerCommunication {
    public SavingsDeposit(ClientStuff clientHandler, JSONArray array) throws SQLException {
        super(clientHandler, array);
        this.accountNumber = clientHandler.getAccountNumber();
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
        String result;
        try {
            if (isTransactionPossible(currentBalance)) {
                currentBalance -= amount;
                int CurrentSavings = dao.getSavingsBalanceAccNum(accountNumber);
                dao.updateSavings(CurrentSavings + amount, accountNumber);
                dao.updateBalance(currentBalance, accountNumber);
                dao.updateTransactionTracker(accountNumber, LocalDate.now(),"Savings Deposit",amount,currentBalance);
                result = "Deposit Successful";
            }
            else{
                result = "Deposit Failed";
            }
            response.put("message", result);
            response.put("Balance",currentBalance);
            dao.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
