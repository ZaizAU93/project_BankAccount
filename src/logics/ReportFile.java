package logics;
import parsing.*;

import javax.xml.transform.sax.SAXSource;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ReportFile {
    private ArrayList<String> report = new ArrayList<>();

    public void setReport(ArrayList<String> report) {
        report = this.report;
    }

    public ArrayList<String> getReport() {
        return report;
    }

    public ArrayList<String> glueReportParsingsTransfer(Parsing parsing, SearchCheck searchCheck) {
        report = parsing.getReportParsing();
        report.addAll(searchCheck.getReadyMoneyTtransfer());
        return report;
    }

    public void writeReportFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/report/report.txt"))) {
            for (String element : report) {
                writer.write(element);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> sortingDate() throws FileNotFoundException {
        Map<String, String> mapReport = new HashMap<>();
        String fileName = "src/report/report.txt";

        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int index = line.indexOf("|");
            if (index != -1) {
                String key = line.substring(0, index);
                String value = line.substring(index + 1);
                //    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss.SSSS.ns");
                //    LocalDateTime Date = LocalDateTime.parse(key, dateTimeFormatter);
                //    LocalDateTime updatedDateTime = Date.plusNanos(100);
                //key = updatedDateTime.toString();
                mapReport.put(key, value);
            }

        }
        return mapReport;
    }

    public void periodOftime(String a, String b) throws FileNotFoundException {

        String filePath = "src/report/report.txt";
        int n = 0; // количество строк
        List<String[]> lines = new ArrayList<>(); // список для хранения строк файла

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split("\\|", 2); // разделение строки на элементы по первому вхождению "|"
                lines.add(cols);
                n++; // увеличение счетчика строк
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] array = new String[n][2]; // создание двумерного массива размером [n][2]

        for (int i = 0; i < n; i++) {
            String[] line = lines.get(i);
            for (int j = 0; j < 2; j++) {
                array[i][j] = line[j];
            }
        }


        int indexA = 0;
        int indexB = 0;
        for(int i = 0 ; i <array.length; i++){
                if (array[i][0].equals(a)){
                    indexA = i;
                }
                if (array[i][0].equals(b)){
                    indexB = i;
                }
            }

        for (int i = indexA; i <= indexB; i++){
            for (int j = 0; j < 2; j++){
                System.out.println(array[i][j]);
            }
            System.out.print(" ");
        }
    }
}


