package Servers.ServerSocket.WebServer;


import io.javalin.Javalin;

public class WebApi {
    private final Javalin server;

    public WebApi() {
        server = Javalin.create();
        this.server.post("/banking", WebApiHandler::clientCommand);
    }

    public void start(int port) {
        this.server.start(port);
    }

    public void stop() {
        this.server.stop();
    }
}
