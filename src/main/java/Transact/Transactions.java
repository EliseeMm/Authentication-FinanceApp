package Transact;

public abstract class Transactions {

    public int currentBalance;
    public final int amount;

    public Transactions(int currentBalance,int amount){
        this.currentBalance = currentBalance;
        this.amount = amount;
    }

    boolean isTransactionPossible(){
        return (this.currentBalance - this.amount) >= 0;
    }

     public int getCurrentBalance(){
        return currentBalance;
    }
}
