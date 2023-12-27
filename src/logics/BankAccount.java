package logics;

public class BankAccount {
    private String check;
    private int sum;

    public BankAccount(String check, int sum) {
        this.check = check;
        this.sum = sum;
    }

    public String getCheck() {
        return check;
    }

    public int getSum() {
        return sum;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public BankAccount bankTransferFrom(BankAccount checkFrom, BankAccount checkTo, int checkSum){
        checkFrom.setSum(checkFrom.sum - checkSum );
        return checkFrom;
    }

    public BankAccount bankTransferTo(BankAccount checkFrom, BankAccount checkTo, int checkSum){
        checkTo.setSum(checkFrom.sum + checkSum);
        return checkTo;
    }

}
