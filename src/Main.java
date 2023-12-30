import logics.ReportFile;
import logics.SearchCheck;
import logics.UpdateCheck;
import parsing.Parsing;
import parsing.ParsingCheck;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);
        System.out.println("1 - Вызов операции парсинга файлов перевода из input");
        System.out.println("2 - вызов операции вывода списка всех переводов из файла отчета.");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Введите число 1 или 2: ");


        Parsing parsing = new Parsing();
        SearchCheck searchCheck = new SearchCheck();


        ParsingCheck parsingCheck = new ParsingCheck();
        searchCheck.searchCheck(parsingCheck, parsing);
        UpdateCheck updateCheck = new UpdateCheck();
        updateCheck.setUpdateMap(updateCheck.updateCheck(searchCheck.getBankAccountArrayList()));
        updateCheck.rewriteFile(updateCheck.updateMap);
        ReportFile reportFile = new ReportFile();
        reportFile.glueReportParsingsTransfer(parsing, searchCheck);
        reportFile.writeReportFile();
        System.out.println("Файл отчет сформирован");

                  //  System.out.println("Введите период");
                  //  reportFile.periodOftime(sc.nextLine(), sc.nextLine());


        DataBase.uploadToDatabase(parsingCheck, reportFile);

        }

}

