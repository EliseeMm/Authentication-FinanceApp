package Servers.ServerSocket.WebServer;

import AccessValidation.ServerCommunication;
import io.javalin.http.Context;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.UUID;

public class WebApiHandler {

    public static void clientCommand(Context context) throws SQLException {
        JSONObject body = new JSONObject(context.body());
        ServerCommunication sc = ServerCommunication.createRequest(body);
        sc.execute();
        context.result(sc.getResponse().toString());
        context.status(201);
    }
}

