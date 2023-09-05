package AccessValidation;

import Servers.ServerSocket.ClientHandler;
import Servers.ServerSocket.ClientStuff;
import org.json.JSONObject;

public interface ServerResponses {
    JSONObject getResponse();

    void execute();
}
