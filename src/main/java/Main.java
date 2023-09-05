
import DatabaseAccess.DatabaseAccessCode;
import Servers.ServerSocket.MainServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class Main {
    
    public static void startServer() throws SQLException, IOException {
        ServerSocket serverSocket1 = new ServerSocket(5000);
        MainServer mainServer = new MainServer(serverSocket1);
        mainServer.run();
    }


    public static void main(String[] args) throws IOException, SQLException {
        DatabaseAccessCode dao = new DatabaseAccessCode("passwords.db");
        dao.initializeDataBase();
        dao.accountNumbers();
        startServer();
    }
}
