package AccessValidation;

import BalanceView.FullStatement;
import BalanceView.MiniStatement;
import DatabaseAccess.DatabaseAccessCode;
import ErrorHandling.InvalidCommand;
import PasswordManagement.PBKDF2;
import Servers.ServerSocket.ClientHandler;
import Servers.ServerSocket.ClientStuff;
import Transact.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public abstract class ServerCommunication implements ServerResponses {
    public ClientStuff clientHandler;
    public final DatabaseAccessCode dao = new DatabaseAccessCode("passwords.db");
    public final PBKDF2 pbkdf2c = new PBKDF2();
    public int currentBalance;
    public String accountNumber;
    public int amount;
    public String recipientAccNum;
    public String reference;
    public JSONArray array;
    public JSONObject response;
    public String result;
    public String message;
    public ServerCommunication(ClientStuff clientStuff) throws SQLException {
        this.clientHandler = clientStuff;
    }

    public ServerCommunication(ClientStuff clientStuff, JSONArray array) throws SQLException {
        this.clientHandler = clientStuff;
        this.array = array;

    }

    public static ServerCommunication createRequest(ClientStuff clientStuff, JSONObject jsonRequest) throws SQLException {

        if(!jsonRequest.isEmpty()) {
            String request = jsonRequest.getString("Request");
            JSONArray arguments = jsonRequest.getJSONArray("Arguments");
            if (request.equals("login") && !LoggedInUsers.isUserLoggedIn(clientStuff)) {
                return new Login(clientStuff, arguments);
            } else if (request.equals("signup") && !LoggedInUsers.isUserLoggedIn(clientStuff)) {
                return new SignUp(clientStuff, arguments);
            } else if (LoggedInUsers.isUserLoggedIn(clientStuff)) {
                return returnTransaction(request,clientStuff,arguments);
            }
            else {
                return new ErrorNotLoggedIn(clientStuff);
            }
        }
        return new InvalidCommand(clientStuff);
    }

    private static ServerCommunication returnTransaction(String request,ClientStuff clientStuff,JSONArray arguments) throws SQLException {
        switch (request) {
            case "payment" -> {
                return new SendMoney(clientStuff, arguments);
            }
            case "savings deposit" -> {
                return new SavingsDeposit(clientStuff, arguments);
            }
            case "savings withdrawal" -> {
                return new SavingsWithdrawal(clientStuff, arguments);
            }
            case "cash withdrawal" -> {
                return new CashWithdrawal(clientStuff, arguments);
            }
            case "cash deposit" -> {
                return new CashDeposit(clientStuff, arguments);
            }
            case "mini statement" -> {
                return new MiniStatement(clientStuff);
            }
            case "full statement" -> {
                return new FullStatement(clientStuff, arguments);
            }
            case "sign out" -> {
                return new SignOut(clientStuff);
            }
            default -> {
                return new InvalidCommand(clientStuff);
            }
        }
    }

    public boolean isTransactionPossible(int currentBalance) {
        return (currentBalance - this.amount) >= 0;
    }
}
