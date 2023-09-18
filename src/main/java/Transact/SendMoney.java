package Transact;

import AccessValidation.ServerCommunication;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class SendMoney extends ServerCommunication {

    public SendMoney(String accountNumber, JSONArray args) throws SQLException {
        super(accountNumber, args);

        this.currentBalance = dao.getCurrentBalanceAccNum(accountNumber);
        this.recipientAccNum = args.get(0).toString();
        this.amount = Integer.parseInt(args.get(1).toString());
        this.reference = args.get(2).toString();
        //R1 for every 100
        int FEE = 1;
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
            if (isTransactionPossible(currentBalance) && validAccount()) {
                currentBalance -= (amount - transactionFee);

                // senders balance
                updateBalances(currentBalance, accountNumber);

                // recipient balance
                int recBalance = dao.getCurrentBalanceAccNum(recipientAccNum);
                updateBalances(recBalance + amount, recipientAccNum);

                //senders statement
                updateAccounts(accountNumber, -amount);

                // recipient statement
                updateAccounts(recipientAccNum, amount);

                result = "OK";
                message = "Payment Successful";
            } else if (!validAccount()) {
                result = "ERROR";
                message = "Invalid Account Number";
            } else {
                result = "ERROR";
                message = "Insufficient funds";
            }
            response.put("result", result);
            response.put("UUID", uuid);
            response.put("message", message);
            response.put("Balance", currentBalance);
            dao.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean validAccount() {
        return dao.validAccountNumber(recipientAccNum);
    }

    private void updateAccounts(String accountNumber, int amount) {
        int currentBalance = getCurrentBalance(accountNumber);
        dao.updateTransactionTracker(accountNumber, LocalDate.now(), reference, amount, -transactionFee, currentBalance);
    }

    private void updateBalances(int currentBalance, String accountNumber) {
        dao.updateBalance(currentBalance, accountNumber);
    }

    private int getCurrentBalance(String accountNumber) {
        return dao.getCurrentBalanceAccNum(accountNumber);
    }
}
