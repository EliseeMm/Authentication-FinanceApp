package Transact;

import AccessValidation.ServerCommunication;
import Servers.ServerSocket.ClientHandler;
import Servers.ServerSocket.ClientStuff;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class CashWithdrawal extends ServerCommunication {
    public CashWithdrawal(ClientStuff clientHandler, JSONArray array) throws SQLException {
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
        response = new JSONObject();
        String result;

        try{
            if(isTransactionPossible(currentBalance)){
                currentBalance -= amount;
                dao.updateBalance(currentBalance,accountNumber);
                dao.updateTransactionTracker(accountNumber, LocalDate.now(),"Cash Withdrawal",-amount,currentBalance);
                result = "Withdrawal Successful";
            }
            else{
                result = "Withdrawal Failed,Insufficient funds";
            }
            response.put("message",result);
            response.put("Balance",currentBalance);
            dao.closeConnection();
        } catch (JSONException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
