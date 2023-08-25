public class Transactions {

    private int currentBalance;

    public Transactions(int currentBalance){
        this.currentBalance = currentBalance;
    }
    public boolean update(int amount){

        if((this.currentBalance - amount) >= 0) {
            this.currentBalance -= currentBalance;
            return true;
        }
        return false;
    }
}
