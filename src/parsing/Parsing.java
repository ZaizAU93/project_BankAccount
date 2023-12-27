package parsing;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;

public class Parsing {
     private ArrayList<String> reportParsing = new ArrayList<>();

    public ArrayList<String> getReportParsing() {
        return reportParsing;
    }

    private ArrayList<String[]> checkTransfer = runDirectory();



     public ArrayList<String[]> getCheckTransfer() {
        return checkTransfer;
    }

    ///public  void setCheckTransfer(ArrayList<String[]> checkTransfer) {
       // this.checkTransfer = checkTransfer;
    //}



    public  ArrayList<String[]> runDirectory() {
        ArrayList<String[]> arrayList = new ArrayList<>();
        String inputDirectory = "src/input";
        String outputDirectory = "src/archive";

        // �������� ���������� ��� �������������, ���� ��� �� ����������
        File outputDir = new File(outputDirectory);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd| HH-mm|");
        String dataFormat = dateTime.format(formatter);
        File inputDir = new File(inputDirectory);
        // ��������, ��� ������� ���������� ����������
        if (inputDir.exists() && inputDir.isDirectory()) {
            File[] files = inputDir.listFiles();
            if (files != null) {
                int i = 0;
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        try {
                            // ������� ����� � ����������� � �����
                            arrayList.add(parseAndMoveFile(file, outputDir));
                            } catch (IOException e) {
                            System.out.println("������ ��� ��������� ����� " + file.getName() + ": " + e.getMessage());
                            reportParsing.add(dataFormat + "|" + file.getName() +" |������ ��� ��������� ����� |"+   ": " + e.getMessage());
                        }
                    }
                }
            }
        } else {
            System.out.println("������� ���������� �� ���������� ��� �� �������� �����������");
        }
        return arrayList;
    }

    public static String[] parseAndMoveFile(File file, File outputDir) throws IOException {
        // ������ ����������� �����
        Path filePath = file.toPath();

        String content = "";
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(filePath.toString());
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);

            byte[] buffer = new byte[10000];

            while (dataInputStream.available() > 0) {
                int bytesRead = dataInputStream.read(buffer);
                content = new String(buffer, 0, bytesRead);
                //  System.out.print(content);
            }

            dataInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // ���������� ����������� ����� �� ������
        String[] lines = content.split("\n");

        // �������� ���������� �����
        if (lines.length < 3) {
            throw new IOException("���� " + file.getName() + " �������� �������� ������");

        }

        String accountFrom = lines[0];
        String accountTo = lines[1];
        String amount = lines[2];


        String pattern = "\\d{5}-\\d{5}";
        String regex = "(?<=\\s)\\d+";
        String regexMinus = "-\\d+";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(lines[0]);
        String checkFrom = "";
        if (matcher.find()) {
            checkFrom = matcher.group();
        }

        Pattern compiledPattern1 = Pattern.compile(pattern);
        Matcher matcher1 = compiledPattern1.matcher(accountTo);
        String checkTo = "";
        if (matcher1.find()) {
            checkTo = matcher1.group();
        }

        Pattern compiledPattern2 = Pattern.compile(regex);
        Matcher matcher2 = compiledPattern2.matcher(amount);
        Pattern compilePattern3 = Pattern.compile(regexMinus);
        Matcher matcher3 = compilePattern3.matcher(amount);
        String amountCheck = "null";
        if (matcher2.find()){
            amountCheck = matcher2.group();
        }else if (matcher3.find()) {
            amountCheck = matcher3.group();
           }


        if (checkFrom.equals("")) {
            throw new IOException("����������� ������ ���������� ���� ����������� � ����� " + file.getName());
        }

        if (checkTo.equals("")) {
            throw new IOException("����������� ������ ���������� ���� ���������� � ����� " + file.getName());
        }


        if ( Integer.parseInt(amountCheck) < 0 ){
            throw new IOException("����� �������� � ����� " + file.getName() + " ������� �������������");
        }

        if ( amountCheck == null){
            throw new IOException("����� �������� � ����� " + file.getName() + " ������� �����������");
        }


        boolean containsOnlyNumbers = amountCheck.matches("^[0-9]+$");
        if (!containsOnlyNumbers){
           throw new IOException("����� �������� � ����� " + file.getName() + " ������� �����������");
        }



        if ( Integer.parseInt(amountCheck) == 0){
            throw new IOException("����� �������� � ����� " + file.getName() + " ������ ������� �������");
        }

        String mas[] = {checkFrom, checkTo, amountCheck,file.getName()};
        // ����������� ����� � �����
        File outputFile = new File(outputDir, file.getName());
        Files.move(filePath, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("���� " + file.getName() + " ������� ��������� � ��������� � �����");


        return mas;
    }
}
