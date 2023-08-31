package DatabaseAccess;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseAccessCode {

    private static final  String DISK_URL = "jdbc:sqlite:";

    public static Connection connection;

    public DatabaseAccessCode(String databaseName) throws SQLException {

        connection = DriverManager.getConnection(DISK_URL+databaseName);
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
    public void accountTransactionsTracker(String accNumber){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS acc"+ accNumber +"("+
                    "transactionID  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "TransactionDate DATE, " +
                    "Reference TEXT, " +
                    "Amount INTEGER, " +
                    "Balance INTEGER" +
                    ")");

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateTransactionTracker(String accNumber, LocalDate date, String ref,int amount,int balance){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO acc"+ accNumber +"("+
                    "TransactionDate," +
                    "Reference,Amount,Balance) " +
                    "VALUES (?,?,?,?)");
            preparedStatement.setString(1, String.valueOf(date));
            preparedStatement.setString(2,ref);
            preparedStatement.setInt(3,amount);
            preparedStatement.setInt(4,balance);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void accountNumbers(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS accountNumbers(" +
                    "accountID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "accountNumber VARCHAR(11), " +
                    "balance INTEGER DEFAULT 1000, " +
                    "savingsBalance INTEGER DEFAULT 0, "+
                    "accountHolderID INTEGER, "+
                    "FOREIGN KEY (accountHolderID) REFERENCES accountHolders (accountHolderID))"
                    );
            preparedStatement.execute();
            preparedStatement.close();
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
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//52802370886
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

    public static String doesAccountNumberExists(String accountNumber){
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
    public int getCurrentBalanceAccNum(String accountNumber){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT balance FROM accountNumbers an " +
                    "WHERE an.accountNumber = ? ");
            ps.setString(1,accountNumber);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return rs.getInt("balance");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getSavingsBalanceAccNum(String accountNumber){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT savingsBalance FROM accountNumbers an " +
                    "WHERE an.accountNumber = ? ");
            ps.setString(1,accountNumber);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return rs.getInt("savingsBalance");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAccountNumber(String userName){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT accountNumber " +
                    "FROM accountNumbers an, accountHolders ah " +
                    "WHERE ah.username = ? " +
                    "AND an.accountHolderId = ah.accountHolderID");
            preparedStatement.setString(1, userName);
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();

            return rs.getString("accountNumber");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validAccountNumber(String accountNumber){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT accountNumber FROM accountNumbers an WHERE an.accountNumber = ?");
            ps.setString(1,accountNumber);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            String accountNumb = rs.getString("accountNumber");
            return accountNumb != null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateBalance(int newBalance, String accountNumber){
        try{
            PreparedStatement ps = connection.prepareStatement("UPDATE accountNumbers SET balance = ? WHERE accountNumber = ? ");
            ps.setInt(1, newBalance);
            ps.setString(2,accountNumber);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSavings(int amount, String accountNumber){
        try{
            PreparedStatement ps = connection.prepareStatement("UPDATE accountNumbers SET savingsBalance = ? WHERE accountNumber = ?");
            ps.setInt(1,amount);
            ps.setString(2,accountNumber);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void closeConnection() throws SQLException {
        connection.close();
    }


}
