package AccountCreation;

import DatabaseAccess.DatabaseAccessCode;

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

    public static void main(String[] args){
        String accNum;
        do {
             accNum = generateAccountNumber();
        } while (DatabaseAccessCode.doesAccountNumberExists(accNum) == null);
        System.out.println(accNum);
    }

}


