package ErrorHandling;

import AccessValidation.ServerCommunication;
import Servers.ClientHandler;
import org.json.JSONObject;

public class InvalidCommand extends ServerCommunication {
    private JSONObject response;
    public InvalidCommand(ClientHandler clientHandler) {
        super(clientHandler);
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        response = new JSONObject();
        response.put("message", "Invalid Command");
    }
}
