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
import java.util.UUID;

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
    public UUID uuid;
    public ServerCommunication(ClientStuff clientStuff) throws SQLException {
        this.clientHandler = clientStuff;
    }

    public ServerCommunication(UUID uuid) throws SQLException{
        this.uuid = uuid;
    }
    public ServerCommunication(UUID uuid,JSONArray array) throws SQLException{
        this.uuid = uuid;
    }

//    public ServerCommunication(ClientStuff clientStuff, JSONArray array) throws SQLException {
//        this.clientHandler = clientStuff;
//        this.array = array;
//
//    }

    public ServerCommunication(String accountNumber,JSONArray array) throws SQLException {
        this.accountNumber = accountNumber;
        this.array = array;
    }
    public ServerCommunication(String accountNumber) throws SQLException {
        this.accountNumber = accountNumber;
    }

    public static ServerCommunication createRequest(JSONObject jsonRequest) throws SQLException {

        if(!jsonRequest.isEmpty()) {
            String request = jsonRequest.getString("Request");
            JSONArray arguments = jsonRequest.getJSONArray("Arguments");
            if (request.equals("login") && !jsonRequest.has("UUID")) {
                return new Login(null, arguments);
            } else if (request.equals("signup") && !jsonRequest.has("UUID")) {
                return new SignUp(null, arguments);
            } else {
                UUID uuid = UUID.fromString(jsonRequest.getString("UUID"));
                if (LoggedInUsers.isUserLoggedIn(uuid)) {
                    String accountNumber = LoggedInUsers.getAccountNumber(uuid);
                    return returnTransaction(request, accountNumber, arguments);
                } else {
                    return new ErrorNotLoggedIn(uuid);
                }
            }
        }
        return null;
    }

    private static ServerCommunication returnTransaction(String request,String accountNumber,JSONArray arguments) throws SQLException {
        switch (request) {
            case "payment" -> {
                return new SendMoney(accountNumber, arguments);
            }
            case "savings deposit" -> {
                return new SavingsDeposit(accountNumber, arguments);
            }
            case "savings withdrawal" -> {
                return new SavingsWithdrawal(accountNumber, arguments);
            }
            case "cash withdrawal" -> {
                return new CashWithdrawal(accountNumber, arguments);
            }
            case "cash deposit" -> {
                return new CashDeposit(accountNumber, arguments);
            }
            case "mini statement" -> {
                return new MiniStatement(accountNumber);
            }
            case "full statement" -> {
                return new FullStatement(accountNumber, arguments);
            }
            case "sign out" -> {
                return new SignOut(accountNumber);
            }
            default -> {
                return null;
//                return new InvalidCommand(accountNumber);
            }
        }
    }

    public boolean isTransactionPossible(int currentBalance) {
        return (currentBalance - this.amount) >= 0;
    }
    public boolean isAccountActive(String username){
        return dao.isAccountActive(username) != null;
    }
}
