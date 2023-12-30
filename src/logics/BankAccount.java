package logics;

public class BankAccount {
    private String check;
    private double sum;

    public BankAccount(String check, double sum) {
        this.check = check;
        this.sum = sum;
    }

    public String getCheck() {
        return check;
    }

    public double getSum() {
        return sum;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public BankAccount bankTransferFrom(BankAccount checkFrom, BankAccount checkTo, double checkSum){
        checkFrom.setSum(checkFrom.sum - checkSum );
        return checkFrom;
    }

    public BankAccount bankTransferTo(BankAccount checkFrom, BankAccount checkTo, int checkSum){
        checkTo.setSum(checkFrom.sum + checkSum);
        return checkTo;
    }

}
