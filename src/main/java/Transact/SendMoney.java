package Transact;

import AccessValidation.ServerCommunication;
import DatabaseAccess.DatabaseAccessCode;
import Servers.ClientHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class SendMoney extends ServerCommunication{
    private final DatabaseAccessCode dao = new DatabaseAccessCode("passwords.db");
    private JSONObject response;
    private final String accountNumber;
    public SendMoney(ClientHandler clientHandler,JSONArray args) throws SQLException {
        super(clientHandler,args);
        this.accountNumber = clientHandler.getAccountNumber();
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
        if(isTransactionPossible(currentBalance) && validAccount()){
            currentBalance -= amount;

            // senders balance
            updateBalances(currentBalance,accountNumber);

            // recipient balance
            int recBalance = dao.getCurrentBalanceAccNum(recipientAccNum);
            updateBalances(recBalance+amount,recipientAccNum);

            //senders statement
            updateAccounts(accountNumber,-amount);

            // recipient statement
            updateAccounts(recipientAccNum,amount);

            result = "Payment Successful";
        } else if (!validAccount()) {
            result = "Invalid Account Number";
        } else {
            result = "Insufficient funds";
        }
        response.put("message",result);
    }
    private boolean validAccount(){
        return dao.validAccountNumber(recipientAccNum);
    }
    private void updateAccounts(String accountNumber,int amount){
        int currentBalance =  getCurrentBalance(accountNumber);
        dao.updateTransactionTracker(accountNumber, LocalDate.now(),reference,amount,currentBalance);
    }
    private void updateBalances(int currentBalance,String accountNumber){
        dao.updateBalance(currentBalance,accountNumber);
    }

    private int getCurrentBalance(String accountNumber){
        return dao.getCurrentBalanceAccNum(accountNumber);
    }
}
