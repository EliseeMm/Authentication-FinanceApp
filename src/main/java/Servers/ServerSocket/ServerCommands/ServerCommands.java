package Servers.ServerSocket.ServerCommands;

import DatabaseAccess.DatabaseAccessCode;
import org.json.JSONObject;

import java.sql.SQLException;

public abstract class ServerCommands {
    public final DatabaseAccessCode dao = new DatabaseAccessCode("passwords.db");
    public JSONObject response = new JSONObject();

    protected ServerCommands() throws SQLException {
    }

    public abstract JSONObject execute();

    public static  ServerCommands returnCommand(String command) throws SQLException {
        String[] mainCommand = command.split(" ");
        switch (mainCommand[0]){
            case "list":
                return new ListAccountHolders();
            case "kill":
                return new CloseServer();
            case "block":
                return new BlockUser(mainCommand[1]);
            case "unblock":
                return new UnblockUser(mainCommand[1]);
            case "sum":
                return new SumOfAccountBalances();
            case "avg":
                return new Averages();
        }
        return null;
    }
}


