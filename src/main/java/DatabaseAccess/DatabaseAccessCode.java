package DatabaseAccess;

import java.sql.*;
public class DatabaseAccessCode {

    private static final  String DISK_URL = "jdbc:sqlite:passwords.db";

    public static final Connection connection;
    static {
        try {
            connection = DriverManager.getConnection(DISK_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DatabaseAccessCode(){
        initializeDataBase();
    }

    public void initializeDataBase(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS passwords(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username VARCHAR(10) NOT NULL, " +
                    "hashPass TEXT NOT NULL)");
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void insertUserNameAndPassword(String userName, String hashPassword){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO passwords(username,hashPass) " +
                    "VALUES(?,?)");
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,hashPassword);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
