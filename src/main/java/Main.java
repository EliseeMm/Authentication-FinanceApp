import DatabaseAccess.DatabaseAccessCode;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SignUp signUp = new SignUp();
        DatabaseAccessCode dao = new DatabaseAccessCode();


        signUp.setLoginDetails();
        String userName = signUp.getUsername();
        String password = signUp.getPassword();


        PBKDF2 pbkdf2c = new PBKDF2();
        String hashPassword = pbkdf2c.generatePasswordHash(password);

        dao.insertUserNameAndPassword(userName,hashPassword);

    }
}
