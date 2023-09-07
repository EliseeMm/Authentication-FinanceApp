package AccessValidation;

import Servers.ServerSocket.ClientStuff;

import java.util.ArrayList;

public class LoggedInUsers {

    public static ArrayList<ClientStuff> users = new ArrayList<>();

    public static void addUser(ClientStuff clientStuff){
        users.add(clientStuff);
    }

    public static void removeUser(ClientStuff clientStuff){
        users.remove(clientStuff);
    }

    public static boolean isUserLoggedIn(ClientStuff clientStuff){
        return users.contains(clientStuff);
    }

    public static ArrayList<ClientStuff> getUsers(){
        return users;
    }
}
