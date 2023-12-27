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
                System.out.println("�������� " + value1 + " ���������� � ������ ������");
                } else {
                System.out.println("�������� " + value1 + " �� ���������� � Map");
            }

            if (check.containsKey(value2)) {
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd| HH-mm|");
                String dataFormat = dateTime.format(formatter);
                bankAccountArrayList.add(new BankAccount(value1, (check.get(value1))-value3));
                bankAccountArrayList.add(new BankAccount(value2, (check.get(value2))+value3));
                readyMoneyTtransfer.add(dataFormat +nameFile +" |"+" ������� � "+value1+ " �� " + value2 + " " + value3 +" ������� ���������");
                System.out.println(dataFormat +" ������� � "+value1+ " �� " + value2 + " "+ array[2] + " " + "������� ���������");
            } else {
                System.out.println("������ � ���������� �����, ���������� �� ����� ���� ������������");
        }


      }
      return readyMoneyTtransfer;
      }
}





