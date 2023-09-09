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
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        response = new JSONObject();


        try{
            if(isTransactionPossible(currentBalance)){
                currentBalance -= amount;
                dao.updateBalance(currentBalance,accountNumber);
                dao.updateTransactionTracker(accountNumber, LocalDate.now(),"Cash Withdrawal",-amount,currentBalance);
                result = "OK";
                message = "Withdrawal Successful";
            }
            else{
                result = "ERROR";
                message = "Withdrawal Failed,Insufficient funds";
            }
            response.put("result",result);
            response.put("UUID",uuid);
            response.put("message",message);
            response.put("Balance",currentBalance);
            dao.closeConnection();
        } catch (JSONException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
