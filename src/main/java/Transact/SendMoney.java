package Transact;

public class SendMoney extends Transactions {

    public SendMoney(int currentBalance, int amount) {
        super(currentBalance, amount);
    }

    public boolean updateBalance(){
        if(isTransactionPossible()){
            currentBalance -= amount;
            return true;
        }
        else {
            System.out.println("Insufficient funds");
            return false;
        }
    }
}
