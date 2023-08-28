package Transact;

public class SendMoney extends Transactions {

    public SendMoney(int currentBalance, int amount) {
        super(currentBalance, amount);
    }

    public void updateBalance(){
        if(isTransactionPossible()){
            currentBalance -= amount;
        }
        else {
            System.out.println("Insufficient funds");
        }
    }
}
