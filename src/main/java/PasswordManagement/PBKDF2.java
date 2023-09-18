package PasswordManagement;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PBKDF2 {

    public String generatePasswordHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 100);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    public byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    public String toHex(byte[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : array) {
            stringBuilder.append(String.format("%02X", b));
        }
        return stringBuilder.toString();
    }

    public boolean doHashPasswordsMatch(String hashPassword, String loginAttempt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] segments = hashPassword.split(":");

        int iterations = Integer.parseInt(segments[0]);
        byte[] salt = getSaltByte(segments[1]);

        char[] chars = loginAttempt.toCharArray();
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 100);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();

        String attemptHash = iterations + ":" + toHex(salt) + ":" + toHex(hash);

        return hashPassword.equals(attemptHash);
    }

    public byte[] getSaltByte(String salts) {
        byte[] bytes = new byte[16];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(salts.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

}
