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
        System.out.println("1 - ����� �������� �������� ������ �������� �� input");
        System.out.println("2 - ����� �������� ������ ������ ���� ��������� �� ����� ������.");
        System.out.println("----------------------------------------------------------------");
        System.out.println("������� ����� 1 ��� 2: ");


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
        System.out.println("���� ����� �����������");

                  //  System.out.println("������� ������");
                  //  reportFile.periodOftime(sc.nextLine(), sc.nextLine());


        DataBase.uploadToDatabase(parsingCheck, reportFile);

        }

}

