package AccessValidation;

import DatabaseAccess.DatabaseAccessCode;
import ErrorHandling.InvalidCommand;
import PasswordManagement.PBKDF2;
import Servers.ClientHandler;
import Transact.CashWithdrawal;
import Transact.SavingsDeposit;
import Transact.SavingsWithdrawal;
import Transact.SendMoney;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public abstract class ServerCommunication implements ServerResponses {
    public ClientHandler clientHandler;
    public final DatabaseAccessCode dao = new DatabaseAccessCode("passwords.db");
    public final PBKDF2 pbkdf2c = new PBKDF2();
    public int currentBalance;
    public String accountNumber;
    public int amount;
    public String recipientAccNum;
    public String reference;
    public JSONArray array;
    public JSONObject response;
    public ServerCommunication(ClientHandler clientHandler) throws SQLException {
        this.clientHandler = clientHandler;
    }

    public ServerCommunication(ClientHandler clientHandler, JSONArray array) throws SQLException {
        this.clientHandler = clientHandler;
        this.array = array;

    }

    public static ServerCommunication createRequest(ClientHandler clientHandler, JSONObject jsonRequest) throws SQLException {

        if(!jsonRequest.isEmpty()) {
            String request = jsonRequest.getString("Request");
            JSONArray arguments = jsonRequest.getJSONArray("Arguments");
            switch (request) {
                case "login" -> {
                    return new Login(clientHandler, arguments);
                }
                case "payment" -> {
                    return new SendMoney(clientHandler, arguments);
                }
                case "signup" -> {
                    return new SignUp(clientHandler, arguments);
                }
                case "savings deposit" ->{
                    return new SavingsDeposit(clientHandler,arguments);
                }
                case "savings withdrawal" ->{
                    return new SavingsWithdrawal(clientHandler,arguments);
                }
                case "cash withdrawal" -> {
                    return new CashWithdrawal(clientHandler,arguments);
                }
                default -> {
                    return new InvalidCommand(clientHandler);
                }
            }
        }
        return new InvalidCommand(clientHandler);
    }

    public boolean isTransactionPossible(int currentBalance) {
        return (currentBalance - this.amount) >= 0;
    }
}
