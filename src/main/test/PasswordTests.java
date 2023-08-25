package src.main.test;

import PasswordManagement.PBKDF2;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTests {

    private static final PBKDF2 pbkdf2 = new PBKDF2();

    @Test
    void passwordCreation() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "123";
        String hashPass = pbkdf2.generatePasswordHash(password);
        assertNotNull(hashPass);
    }

    @Test
    void samePasswordButDifferentHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "123";
        String hashPass = pbkdf2.generatePasswordHash(password);

        String password1 = "456";
        String hashPass1 = pbkdf2.generatePasswordHash(password1);

        assertNotEquals(hashPass1,hashPass);
    }

    @Test
    void confirmThatPasswordMatchesForLogin() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "test1?asa";
        String hashPass = pbkdf2.generatePasswordHash(password);
        assertTrue(pbkdf2.doHashPasswordsMatch(hashPass,password));
    }

}
