package AccessValidation;

import ErrorHandling.InvalidCommand;
import Servers.ClientHandler;
import Transact.SendMoney;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public abstract class ServerCommunication implements ServerResponses {
    public ClientHandler clientHandler;
    public int currentBalance;
    public int amount;
    public String recipientAccNum;
    public String reference;

    public ServerCommunication(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ServerCommunication(ClientHandler clientHandler, JSONArray array) {
        this.clientHandler = clientHandler;
        this.recipientAccNum = array.get(0).toString();
        this.amount = Integer.parseInt(array.get(1).toString());
        this.reference = array.get(2).toString();

    }

    public static ServerCommunication createRequest(ClientHandler clientHandler, JSONObject jsonRequest) throws SQLException {

        if(!jsonRequest.isEmpty()) {
            String request = jsonRequest.getString("Request");
            JSONArray arguments = jsonRequest.getJSONArray("Arguments");
            System.out.println(jsonRequest);
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
