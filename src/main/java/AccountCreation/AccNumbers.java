package AccountCreation;


import java.util.Random;

public class AccNumbers {

    private static final Random random = new Random();

    public static String generateAccountNumber() {
        StringBuilder num = new StringBuilder();

        while (num.length()< 11) {
            int anInt = random.nextInt(9);
            num.append(anInt);
        }

        return num.toString();
    }
}


