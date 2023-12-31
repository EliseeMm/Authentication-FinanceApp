package DatabaseAccess;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseAccessCode {

    private static final String DISK_URL = "jdbc:sqlite:";

    public static Connection connection;

    public DatabaseAccessCode(String databaseName) throws SQLException {

        connection = DriverManager.getConnection(DISK_URL + databaseName);
    }

    public void initializeDataBase() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS accountHolders(" +
                    "accountHolderID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username VARCHAR(10) NOT NULL, " +
                    "activeAccount BOOLEAN DEFAULT TRUE, " +
                    "hashPass TEXT NOT NULL" +
                    ")");
            preparedStatement.execute();
            userDetails();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void accountTransactionsTracker(String accNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS acc" + accNumber + "(" +
                    "transactionID  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "TransactionDate DATE, " +
                    "TransactionDateValue DATE, " +
                    "Reference TEXT, " +
                    "Amount INTEGER, " +
                    "Fee INTEGER, " +
                    "Balance INTEGER" +
                    ")");

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTransactionTracker(String accNumber, LocalDate date, String ref, int amount, int fee, int balance) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO acc" + accNumber + "(" +
                    "TransactionDate,TransactionDateValue," +
                    "Reference,Amount,Fee,Balance) " +
                    "VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, String.valueOf(date));
            preparedStatement.setDate(2, Date.valueOf(String.valueOf(date)));
            preparedStatement.setString(3, ref);
            preparedStatement.setInt(4, amount);
            preparedStatement.setInt(5, fee);
            preparedStatement.setInt(6, balance);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void accountNumbers() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS accountNumbers(" +
                    "accountID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "accountNumber VARCHAR(11), " +
                    "balance INTEGER DEFAULT 1000, " +
                    "savingsBalance INTEGER DEFAULT 0, " +
                    "accountHolderID INTEGER, " +
                    "FOREIGN KEY (accountHolderID) REFERENCES accountHolders (accountHolderID))"
            );
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void userDetails() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS userDetails(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name VARCHAR NOT NULL, " +
                    "surname VARCHAR NOT NULL, " +
                    "IDNumber VARCHAR NOT NULL, " +
                    "email VARCHAR, " +
                    "address VARCHAR, " +
                    "phone VARCHAR, " +
                    "userID INTEGER, " +
                    "FOREIGN KEY (userID) REFERENCES accountHolders (accountHolderID))"
            );
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUserDetails(String name, String surname, String IDNumber, String email, String address, String phone, int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO userDetails(name,surname," +
                    "IDNumber,email,address,phone,userID) VALUES(?,?,?,?,?,?,?)"
            );
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, IDNumber);
            ps.setString(4, email);
            ps.setString(5, address);
            ps.setString(6, phone);
            ps.setInt(7, id);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void insertUserNameAndPassword(String userName, String hashPassword) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO accountHolders(username,hashPass) " +
                    "VALUES(?,?)");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, hashPassword);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //52802370886
    public String getHashPassword(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT hashPass FROM accountHolders WHERE username = ?");
            preparedStatement.setString(1, username);
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();

            return rs.getString("hashPass");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPrimaryID(String username) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT accountHolderID FROM accountHolders WHERE username = ? ");
            ps.setString(1, username);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return rs.getInt("accountHolderID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAccountNumber(int accountHolderID, String accNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO accountNumbers(accountNumber,accountHolderID) VALUES(?,?)");
            ps.setString(1, accNumber);
            ps.setInt(2, accountHolderID);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCurrentBalanceAccNum(String accountNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT balance FROM accountNumbers an " +
                    "WHERE an.accountNumber = ? ");
            ps.setString(1, accountNumber);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return rs.getInt("balance");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getSavingsBalanceAccNum(String accountNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT savingsBalance FROM accountNumbers an " +
                    "WHERE an.accountNumber = ? ");
            ps.setString(1, accountNumber);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return rs.getInt("savingsBalance");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAccountNumber(String userName) {
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

    public boolean validAccountNumber(String accountNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT accountNumber FROM accountNumbers an WHERE an.accountNumber = ?");
            ps.setString(1, accountNumber);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            String accountNumb = rs.getString("accountNumber");
            return accountNumb != null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBalance(int newBalance, String accountNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE accountNumbers SET balance = ? WHERE accountNumber = ? ");
            ps.setInt(1, newBalance);
            ps.setString(2, accountNumber);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSavings(int amount, String accountNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE accountNumbers SET savingsBalance = ? WHERE accountNumber = ?");
            ps.setInt(1, amount);
            ps.setString(2, accountNumber);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAccStatement(LocalDate date, String accountNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("Select transactionID,TransactionDate,Reference,Amount,Fee," +
                    "Balance from acc" + accountNumber + " ac WHERE ac.TransactionDateValue < ? ");
            ps.setDate(1, Date.valueOf(date));
            ps.execute();
            return ps.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAccountHolders() {
        try {
            PreparedStatement ps = connection.prepareStatement("Select accountHolders.accountHolderID,accountHolders." +
                    "username,accountNumbers.accountNumber ,accountNumbers.balance,accountNumbers.savingsBalance " +
                    "FROM accountHolders " +
                    "INNER JOIN accountNumbers ON  accountHolders.accountHolderID = accountNumbers.accountHolderID ");
            ps.execute();
            return ps.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String isAccountActive(String username) {
        try {
            PreparedStatement ps = connection.prepareStatement("Select username FROM accountHolders WHERE activeAccount = TRUE AND username =?");
            ps.setString(1, username);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            System.out.println(rs.getString("username"));
            return rs.getString("username");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void blockOrUnblock(boolean blockOrUnblock, String username) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE  accountHolders SET activeAccount = ? WHERE username = ?");
            ps.setBoolean(1, blockOrUnblock);
            ps.setString(2, username);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTotalUsersAmount() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT SUM(balance) FROM accountNumbers");
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getAverage() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT AVG(balance) FROM accountNumbers");
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getAccountUserId(String accountNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT accountHolderID FROM accountNumbers WHERE accountNumber = ?");
            ps.setString(1, accountNumber);
            ps.execute();
            return ps.getResultSet().getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getUserDetails(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT name,surname,IDNumber,email,address,phone FROM userDetails WHERE userID=?");
            ps.setInt(1, id);
            ps.execute();
            return ps.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet aboveOrBelowAverage(String aboveOrBelow, int avg) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT accountHolderID,accountNumber,balance FROM accountNumbers WHERE balance " + aboveOrBelow + "?");
            ps.setInt(1, avg);
            ps.execute();
            return ps.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void closeConnection() throws SQLException {
        connection.close();
    }


}
