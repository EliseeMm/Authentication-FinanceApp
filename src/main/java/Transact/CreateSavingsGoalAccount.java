package Transact;

import AccessValidation.ServerCommunication;
import Servers.ClientHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public class CreateSavingsGoalAccount extends ServerCommunication {
    public CreateSavingsGoalAccount(ClientHandler clientHandler, JSONArray arguments) throws SQLException {
        super(clientHandler,arguments);
    }

    @Override
    public JSONObject getResponse() {
        return null;
    }

    @Override
    public void execute() {

    }
}
