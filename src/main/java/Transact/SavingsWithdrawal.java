package Transact;

import AccessValidation.ServerCommunication;
import Servers.ServerSocket.ClientHandler;
import Servers.ServerSocket.ClientStuff;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class SavingsWithdrawal extends ServerCommunication {
    public SavingsWithdrawal(ClientStuff clientHandler, JSONArray array) throws SQLException {
        super(clientHandler, array);
        this.accountNumber = clientHandler.getAccountNumber();
        this.amount = Integer.parseInt(array.get(0).toString());
        this.currentBalance = dao.getCurrentBalanceAccNum(accountNumber);
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        String result;
        response = new JSONObject();
        try {
            int savingsBalance = dao.getSavingsBalanceAccNum(accountNumber);
            if (isTransactionPossible(savingsBalance)) {
                currentBalance += amount;
                dao.updateSavings(savingsBalance - amount, accountNumber);
                dao.updateBalance(currentBalance, accountNumber);
                dao.updateTransactionTracker(accountNumber, LocalDate.now(),"Savings Deposit",amount,currentBalance);
                result = "Withdrawal Successful";
            }
            else {
                result = "Withdrawal failed";
            }
            response.put("message",result);
            response.put("Balance",currentBalance);
            dao.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
