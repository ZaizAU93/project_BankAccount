package logics;

import parsing.ParsingCheck;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class UpdateCheck extends ParsingCheck {

    public Map<String, Double> updateMap;
    public ArrayList<BankAccount> checkUpdate;

    public void setUpdateMap(Map<String, Double> updateMap) {
        this.updateMap = updateMap;
    }

    @Override
    public Map<String, Double> readAccountCheckFile() {
        return super.readAccountCheckFile();
    }

   public Map<String, Double> updateCheck(ArrayList<BankAccount> checkUpdate){
        Map<String, Double> bankAccount = readAccountCheckFile();
        for(BankAccount account : checkUpdate) {
           String accountName = account.getCheck();
           double accountSum = account.getSum();

           if(bankAccount.containsKey(accountName)) {
               bankAccount.put(accountName, accountSum);
           }
       }
    return bankAccount;
   }


    public  void rewriteFile(Map<String, Double> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter( "src/check/check.txt"))) {
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                String key = entry.getKey();
                double value = entry.getValue();
                writer.write(key + " " + value);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
