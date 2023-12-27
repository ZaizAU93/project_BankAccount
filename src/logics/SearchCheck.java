package logics;


import parsing.Parsing;
import parsing.ParsingCheck;

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
          checkTransfers = parsing.runDirectory();


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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd| HH-mm|");
                String dataFormat = dateTime.format(formatter);
                bankAccountArrayList.add(new BankAccount(value1, (check.get(value1))-value3));
                bankAccountArrayList.add(new BankAccount(value2, (check.get(value2))+value3));
                readyMoneyTtransfer.add(dataFormat +nameFile +" |"+" перевод с "+value1+ " на " + value2 + " " + value3 +" успешно обработан");
                System.out.println(dataFormat +" перевод с "+value1+ " на " + value2 + " "+ array[2] + " " + "успешно обработан");
            } else {
                System.out.println("Ошибка в банковском счете, транзакция не может быть осуществлена");
        }


      }
      return readyMoneyTtransfer;
      }
}





