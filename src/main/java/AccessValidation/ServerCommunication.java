package AccessValidation;

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


    public ServerCommunication(ClientHandler clientHandler, int amount){
        this.clientHandler = clientHandler;
        this.amount = amount;
    }

    public ServerCommunication(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }
    public ServerCommunication(ClientHandler clientHandler,JSONArray array){
        this.clientHandler = clientHandler;
        this.recipientAccNum =  array.get(0).toString();
        this.amount = Integer.parseInt(array.get(1).toString());
        this.reference = array.get(2).toString();

    }

    public boolean isTransactionPossible(int currentBalance){
        return (currentBalance - this.amount) >= 0;
    }

    public int getCurrentBalance(){
        return currentBalance;
    }

    public static ServerCommunication createRequest(ClientHandler clientHandler,JSONObject jsonRequest) throws SQLException {

        String request = jsonRequest.getString("Request");
        JSONArray arguments = jsonRequest.getJSONArray("Arguments");
        System.out.println(jsonRequest);
        switch (request){
            case "login" -> {
                return new Login(clientHandler,arguments);
            }
            case "payment" -> {
                return new SendMoney(clientHandler,arguments);
            }
            default -> {
                return null;
            }
        }
    }
}
