package logics;


import parsing.Parsing;
import parsing.ParsingCheck;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.time.LocalDateTime;

public class SearchCheck{
       private Map<String, Integer> check;
       private ArrayList<String[]> checkTransfers;

       private Map<String, Integer> result;
        public static  ArrayList <BankAccount> bankAccountArrayList = new ArrayList<>();

        public ArrayList<BankAccount> getBankAccountArrayList() {
        return bankAccountArrayList;
       }

        private ArrayList<String> readyMoneyTtransfer = new  ArrayList();

        public ArrayList<String> getReadyMoneyTtransfer() {
        return readyMoneyTtransfer;
    }

         public ArrayList<String> searchCheck(ParsingCheck parsingCheck, Parsing parsing) {


          check = parsingCheck.readAccountCheckFile();
        try {
            checkTransfers = parsing.runDirectory();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        for (String[] array : parsing.getCheckTransfer()) {
          String value1 = array[0];
          String value2 = array[1];
          int value3 = Integer.parseInt(array[2]);
          String nameFile = array[3];

            if (check.containsKey(value1)) {
                System.out.println("Значение " + value1 + " содержится в списке счетов");
                } else {
                System.out.println("Значение " + value1 + " не содержится в Map");
            }

            if (check.containsKey(value2)) {
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss.SSSS|");
                String dataFormat = dateTime.format(formatter);
                if ((check.get(value1) - value3) < 0){
                    readyMoneyTtransfer.add(dataFormat +nameFile +" |"+" перевод с "+value1+ " на " + value2 + " " + value3 +" невозможно осуществить транзакцию, на "+ value1+ " нелостаточно средств ");
                } else {
                bankAccountArrayList.add(new BankAccount(value1, (check.get(value1))-value3));
                bankAccountArrayList.add(new BankAccount(value2, (check.get(value2))+value3));
                readyMoneyTtransfer.add(array[4] + nameFile +" |"+" перевод с "+value1+ " на " + value2 + " " + value3 +" успешно обработан");
                System.out.println(array[4] +" перевод с "+value1+ " на " + value2 + " "+ array[2] + " " + "успешно обработан");
            }
        }

          try {
              Thread.sleep(10);
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
      }
      return readyMoneyTtransfer;
      }
}





