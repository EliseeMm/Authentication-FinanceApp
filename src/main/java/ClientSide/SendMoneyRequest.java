package ClientSide;

import org.json.JSONObject;

import java.util.Scanner;

public class SendMoneyRequest implements Requests {
    private final JSONObject request = new JSONObject();
    private final Scanner scanner = new Scanner(System.in);

    public JSONObject getRequest(){
        System.out.println("Recipient Account Number");
        String accNum = scanner.nextLine();

        System.out.println("Enter Amount: ");
        String amount = scanner.nextLine();

        System.out.println("Enter ref: ");
        String reference = scanner.nextLine();

        String[] arguments = {accNum,amount,reference};
        request.put("arguments",arguments);

        return request;
    }
}
