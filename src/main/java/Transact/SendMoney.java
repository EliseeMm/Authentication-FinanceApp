package Transact;

import AccessValidation.ServerCommunication;
import Servers.ServerSocket.ClientHandler;
import Servers.ServerSocket.ClientStuff;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class SendMoney extends ServerCommunication{

    public SendMoney(ClientStuff clientHandler, JSONArray args) throws SQLException {
        super(clientHandler,args);
        this.accountNumber = clientHandler.getAccountNumber();
        this.currentBalance = dao.getCurrentBalanceAccNum(accountNumber);
        this.recipientAccNum = args.get(0).toString();
        this.amount = Integer.parseInt(args.get(1).toString());
        this.reference = args.get(2).toString();
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
            if (isTransactionPossible(currentBalance) && validAccount()) {
                currentBalance -= amount;

                // senders balance
                updateBalances(currentBalance, accountNumber);

                // recipient balance
                int recBalance = dao.getCurrentBalanceAccNum(recipientAccNum);
                updateBalances(recBalance + amount, recipientAccNum);

                //senders statement
                updateAccounts(accountNumber, -amount);

                // recipient statement
                updateAccounts(recipientAccNum, amount);

                result = "Payment Successful";
            } else if (!validAccount()) {
                result = "Invalid Account Number";
            } else {
                result = "Insufficient funds";
            }
            response.put("message", result);
            response.put("Balance",currentBalance);
            dao.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
