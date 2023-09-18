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

    public static ServerCommands returnCommand(String command) throws SQLException {
        String[] mainCommand = command.split(" ");
        return switch (mainCommand[0]) {
            case "list" -> new ListAccountHolders();
            case "kill" -> new CloseServer();
            case "block" -> new BlockUser(mainCommand[1]);
            case "unblock" -> new UnblockUser(mainCommand[1]);
            case "sum" -> new SumOfAccountBalances();
            case "avg" -> new Averages();
            case "info" -> new RetrieveAccountHolderDetails(mainCommand[1]);
            default -> null;
        };
    }
}


