package src.main.test;

import DatabaseAccess.DatabaseAccessCode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DataBaseTests {

    private static DatabaseAccessCode databaseAccessCode;
    @BeforeAll
    static void initialiseDatabase() throws SQLException {
        databaseAccessCode = new DatabaseAccessCode("TestDataBase.db");
    }

    @AfterAll
    static void deleteDatabase(){
        File database = new File("TestDataBase.db");

        if(database.delete()){
            System.out.println("Deleted");
        }
    }
    @Test
    void insertData() {
        String userName = "testUser";
        String hashPass = "aas22323dssd";
        databaseAccessCode.insertUserNameAndPassword(userName, hashPass);

//         int id = databaseAccessCode.getPrimaryID(userName);

    }
}
