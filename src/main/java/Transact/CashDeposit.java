package Transact;

import AccessValidation.ServerCommunication;
import Servers.ServerSocket.ClientHandler;
import Servers.ServerSocket.ClientStuff;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class CashDeposit extends ServerCommunication {
    public CashDeposit(ClientStuff clientHandler, JSONArray array) throws SQLException {
        super(clientHandler, array);
        this.amount = Integer.parseInt(array.get(0).toString());
        this.accountNumber = clientHandler.getAccountNumber();
        this.currentBalance = dao.getCurrentBalanceAccNum(accountNumber);
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {

        String result;
        try {
            response = new JSONObject();
            currentBalance += amount;
            dao.updateBalance(currentBalance, accountNumber);
            dao.updateTransactionTracker(accountNumber, LocalDate.now(), "Cash Deposit", amount, currentBalance);
            result = "Successful Deposit";
            response.put("message",result);
            response.put("Balance",currentBalance);
            dao.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
