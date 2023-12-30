import logics.BankAccount;
import logics.ReportFile;
import logics.SearchCheck;
import parsing.Parsing;
import parsing.ParsingCheck;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.Class.forName;

public class DataBase {
    static String url = "jdbc:mysql://localhost:3306/root";

    static String user = "root";
    static String password = "!12345678q";


    public static void uploadToDatabase(ParsingCheck check, ReportFile reportFile) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();


        String query = "INSERT INTO BanckAccount (checkB, sum) VALUES ( ?, ?)";
        String queryOperations = "INSERT INTO operations (data, operations) VALUES ( ?, ?)";
        Connection connection;

        connection = DriverManager.getConnection(url, user, password);

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        PreparedStatement preparedStatement1 = connection.prepareStatement(queryOperations);


        Map<String, Integer> mapDataCheck = check.readAccountCheckFile();
        Map<String, String> mapReportFile;
        try {
            mapReportFile = reportFile.sortingDate();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<String, Integer> entry : mapDataCheck.entrySet()) {
            preparedStatement.setString(1, entry.getKey());
            preparedStatement.setDouble(2, entry.getValue());
            preparedStatement.executeUpdate();
        }

        for (Map.Entry<String, String> entry : mapReportFile.entrySet()) {
            preparedStatement1.setString(1, entry.getKey());
            preparedStatement1.setString(2, entry.getValue());
            preparedStatement1.executeUpdate();
        }


        //   preparedStatement.executeUpdate();

        preparedStatement.close();
        preparedStatement1.close();
        connection.close();

        System.out.println("Data uploaded successfully!");

    }
}



