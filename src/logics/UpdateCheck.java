package logics;

import parsing.ParsingCheck;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class UpdateCheck extends ParsingCheck {

    public Map<String, Integer> updateMap;
    public ArrayList<BankAccount> checkUpdate;

    public void setUpdateMap(Map<String, Integer> updateMap) {
        this.updateMap = updateMap;
    }

    @Override
    public Map<String, Integer> readAccountCheckFile() {
        return super.readAccountCheckFile();
    }

   public Map<String, Integer> updateCheck(ArrayList<BankAccount> checkUpdate){
        Map<String, Integer> bankAccount = readAccountCheckFile();
        for(BankAccount account : checkUpdate) {
           String accountName = account.getCheck();
           int accountSum = account.getSum();

           if(bankAccount.containsKey(accountName)) {
               bankAccount.put(accountName, accountSum);
           }
       }
    return bankAccount;
   }


    public  void rewriteFile(Map<String, Integer> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter( "src/check/check.txt"))) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                writer.write(key + " " + value);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
