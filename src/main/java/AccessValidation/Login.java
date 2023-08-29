package AccessValidation;

import DatabaseAccess.DatabaseAccessCode;
import PasswordManagement.PBKDF2;
import Servers.ClientHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Scanner;

public class Login extends ServerCommunication{
    private PBKDF2 pbkdf2c = new PBKDF2();
    DatabaseAccessCode dao = new DatabaseAccessCode("passwords.db");
    private String username;
    private JSONObject response;
    private String password;
    private static Scanner sc = new Scanner(System.in);

    public Login(ClientHandler clientHandler, JSONArray arguments) throws SQLException {
        super(clientHandler);
        this.username = arguments.get(0).toString();
        this.password = arguments.get(1).toString();
    }
    public void setUsername(){
        System.out.println("Enter Username");
        this.username = sc.nextLine();
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void execute() {
        String result;
        response = new JSONObject();
        String hashSaved = dao.getHashPassword(username);
        try {
            if(pbkdf2c.doHashPasswordsMatch(hashSaved, password)){
                result = "Login Successful";
                this.clientHandler.setAccountNumber(dao.getAccountNumber(username));
            }
            else{
                result = "Login failed";
            }
            response.put("message",result);
            dao.closeConnection();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
