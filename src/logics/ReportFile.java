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

    public  Map<String, String> sortingDate() throws FileNotFoundException {
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
                mapReport.put(key, value);
            }

        }
        return mapReport;
    }


    public void periodOftime(String a, String b) throws FileNotFoundException {

        String filePath = "src/report/report.txt";
        int n = 0;
        List<String[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split("\\|", 2);
                lines.add(cols);
                n++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] array = new String[n][2];

        for (int i = 0; i < n; i++) {
            String[] line = lines.get(i);
            for (int j = 0; j < 2; j++) {
                array[i][j] = line[j];
            }
        }


        int indexA = 0;
        int indexB = 0;

        boolean isA = false;
        boolean isB = false;

        for(int i = 0 ; i <array.length; i++){
                if (array[i][0].equals(a)){
                    indexA = i;
                    isA = true;
                }
                if (array[i][0].equals(b)){
                    indexB = i;
                    isB = true;
                }

                }


       if (isA == true && isB == true) {
           for (int i = indexA; i <= indexB; i++) {
               for (int j = 0; j < 2; j++) {
                   System.out.print(array[i][j] + " ");
               }
               System.out.println(" ");
           }
       }else if (isA == false){
           System.out.println("Вы ввели начальную точку не из файла-отчета. Прочтите файл Readme и введите верно");
       }else if (isB){
           System.out.println("Вы ввели конечную точку не из файла-отчета. Прочтите файл Readme и введите верно");
       }else if (isA == false && isB == false){
           System.out.println("Вы ввели период которого нет в файле-отчете. Прочтите файл Readme и введите верно");
       };
    }
}


