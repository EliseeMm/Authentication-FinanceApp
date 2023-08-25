package DatabaseAccess;

import java.sql.*;
import java.util.Arrays;

public class DatabaseAccessCode {

    private static final  String DISK_URL = "jdbc:sqlite:";

    public static Connection connection;

    public DatabaseAccessCode(String databaseName) throws SQLException {

        connection = DriverManager.getConnection(DISK_URL+databaseName);
        initializeDataBase();
        accountNumbers();
    }

    public void initializeDataBase(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS accountHolders(" +
                    "accountHolderID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "username VARCHAR(10) NOT NULL, " +
                    "hashPass TEXT NOT NULL" +
                    ")");
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void accountNumbers(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS accountNumbers(" +
                    "accountID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "accountNumber VARCHAR(11), " +
                    "balance INTEGER DEFAULT 0, " +
                    "accountHolderID INTEGER, "+
                    "FOREIGN KEY (accountHolderID) REFERENCES accountHolders (accountHolderID))"
                    );
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void insertUserNameAndPassword(String userName, String hashPassword){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO accountHolders(username,hashPass) " +
                    "VALUES(?,?)");
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,hashPassword);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHashPassword(String username){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT hashPass FROM accountHolders WHERE username = ?");
            preparedStatement.setString(1,username);
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();

            return rs.getString("hashPass");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String doesAccountNumberExists(String accountNumber) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT accountNumber FROM accountNumbers WHERE accountNumber = ?");
            preparedStatement.setString(1, accountNumber);
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();

            return rs.getString("accountNumber");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPrimaryID(String username){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT accountHolderID FROM accountHolders WHERE username = ? ");
            ps.setString(1,username);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return rs.getInt("accountHolderID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addAccountNumber(int accountHolderID,String accNumber){
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO accountNumbers(accountNumber,accountHolderID) VALUES(?,?)");
            ps.setString(1,accNumber);
            ps.setInt(2,accountHolderID);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getOpeningBalance(String userName){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT balance FROM accountNumbers an,accountHolders ah " +
                    "WHERE ah.username = ? " +
                    "AND an.accountHolderId = ah.accountHolderID");
            ps.setString(1,userName);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return rs.getInt("balance");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
