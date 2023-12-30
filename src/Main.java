
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
        System.out.println("1 - Вызов операции парсинга файлов перевода из input");
        System.out.println("2 - вызов операции вывода списка всех переводов из файла отчета.");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Введите число 1: ");



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
                    System.out.println("Парсинг файлов завершен");
                    System.out.println("Для формирования файла отчета нажмите 2");
                } else if (input == 2) {
                    if (searchCheck != null && parsingCheck != null) {
                        reportFile = new ReportFile();
                        reportFile.glueReportParsingsTransfer(parsing, searchCheck);
                        reportFile.writeReportFile();
                        System.out.println("Файл отчет сформирован");
                        System.out.println("Для вывода операций за определенный период введите 3");
                    } else {
                        System.out.println("Неверный ввод. Вариант 2 можно выбрать только после выполнения варианта 1.");
                    }
                } else if (input == 3) {
                    if (reportFile != null) {
                        System.out.println("Введите начальную точку отсчета (дату из файла-отчета):");
                        sc.nextLine();
                        String a = sc.nextLine();
                        System.out.println("Введите конечную временную точку(дату из файла-отчета):");
                        String b = sc.nextLine();
                        reportFile.periodOftime(a, b);
                        System.out.println("Для добавления результатов парсинга в БД нажмите 4");
                    } else {
                        System.out.println("Неверный ввод. Вариант 3 можно выбрать только после выполнения варианта 2.");
                    }
                } else if (input == 4) {
                    if (parsingCheck != null && reportFile != null) {
                        dataBase = new DataBase();
                        dataBase.uploadToDatabase(parsingCheck, reportFile);
                        isValidInput = true;
                    } else {
                        System.out.println("Неверный ввод. Вариант 4 можно выбрать только после выполнения варианта 3.");
                    }
                } else {
                    System.out.println("Неправильный ввод.");
                }
            } else {
                System.out.println("Ввод чисел должен быть последовательным.");
            }
        }
    }
}