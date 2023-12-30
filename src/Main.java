
import logics.ReportFile;
import logics.SearchCheck;
import logics.UpdateCheck;
import parsing.Parsing;
import parsing.ParsingCheck;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);
        System.out.println("1 - ����� �������� �������� ������ �������� �� input");
        System.out.println("2 - ����� �������� ������ ������ ���� ��������� �� ����� ������.");
        System.out.println("----------------------------------------------------------------");
        System.out.println("������� ����� 1: ");



        Parsing parsing = null;
        SearchCheck searchCheck = null;
        ParsingCheck parsingCheck = null;
        UpdateCheck updateCheck = null;
        ReportFile reportFile = null;
        DataBase dataBase = null;
        boolean isValidInput = false;
        int prevInput = 0;

        while (!isValidInput) {
            int input = sc.nextInt();
            if (input == prevInput + 1) {
                prevInput = input;
                if (input == 1) {
                    parsing = new Parsing();
                    searchCheck = new SearchCheck();
                    parsingCheck = new ParsingCheck();
                    searchCheck.searchCheck(parsingCheck, parsing);
                    updateCheck = new UpdateCheck();
                    updateCheck.setUpdateMap(updateCheck.updateCheck(searchCheck.getBankAccountArrayList()));
                    updateCheck.rewriteFile(updateCheck.updateMap);
                    System.out.println("������� ������ ��������");
                    System.out.println("��� ������������ ����� ������ ������� 2");
                } else if (input == 2) {
                    if (searchCheck != null && parsingCheck != null) {
                        reportFile = new ReportFile();
                        reportFile.glueReportParsingsTransfer(parsing, searchCheck);
                        reportFile.writeReportFile();
                        System.out.println("���� ����� �����������");
                        System.out.println("��� ������ �������� �� ������������ ������ ������� 3");
                    } else {
                        System.out.println("�������� ����. ������� 2 ����� ������� ������ ����� ���������� �������� 1.");
                    }
                } else if (input == 3) {
                    if (reportFile != null) {
                        System.out.println("������� ��������� ����� ������� (���� �� �����-������):");
                        sc.nextLine();
                        String a = sc.nextLine();
                        System.out.println("������� �������� ��������� �����(���� �� �����-������):");
                        String b = sc.nextLine();
                        reportFile.periodOftime(a, b);
                        System.out.println("��� ���������� ����������� �������� � �� ������� 4");
                    } else {
                        System.out.println("�������� ����. ������� 3 ����� ������� ������ ����� ���������� �������� 2.");
                    }
                } else if (input == 4) {
                    if (parsingCheck != null && reportFile != null) {
                        dataBase = new DataBase();
                        dataBase.uploadToDatabase(parsingCheck, reportFile);
                        isValidInput = true;
                    } else {
                        System.out.println("�������� ����. ������� 4 ����� ������� ������ ����� ���������� �������� 3.");
                    }
                } else {
                    System.out.println("������������ ����.");
                }
            } else {
                System.out.println("���� ����� ������ ���� ����������������.");
            }
        }
    }
}