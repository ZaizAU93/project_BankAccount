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

    public Parsing() throws InterruptedException {
    }

    public ArrayList<String> getReportParsing() {
        return reportParsing;
    }

    private ArrayList<String[]> checkTransfer = runDirectory();



     public ArrayList<String[]> getCheckTransfer() {
        return checkTransfer;
    }

    public  ArrayList<String[]> runDirectory() throws InterruptedException {
        ArrayList<String[]> arrayList = new ArrayList<>();
        String inputDirectory = "src/input";
        String outputDirectory = "src/archive";

        File outputDir = new File(outputDirectory);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss.SSSS.ns|");

        File inputDir = new File(inputDirectory);
        // Проверка, что входная директория существует
        if (inputDir.exists() && inputDir.isDirectory()) {
            File[] files = inputDir.listFiles();
            if (files != null) {
                int i = 0;
                for (File file : files) {
                    String dataFormat = dateTime.format(formatter);

                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        try {
                            // Парсинг файла и перемещение в архив
                            arrayList.add(parseAndMoveFile(file, outputDir));
                            } catch (IOException e) {
                            reportParsing.add( e.getMessage());

                        }
                    }
                }
            }
        } else {
            System.out.println("Входная директория не существует или не является директорией");
        }
        return arrayList;
    }

    public static String[] parseAndMoveFile(File file, File outputDir) throws IOException, InterruptedException {
        Path filePath = file.toPath();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss.SSSS.ns|");
        String content = "";
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(filePath.toString());
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);

            byte[] buffer = new byte[10000];

            while (dataInputStream.available() > 0) {
                int bytesRead = dataInputStream.read(buffer);
                content = new String(buffer, 0, bytesRead);
            }

            dataInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] lines = content.split("\n");

        if (lines.length < 3) {
            throw new IOException(dateTime.format(formatter) +file.getName()+ "| Файл  содержит неполные данные");

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

        Thread.sleep(100);
        if (checkFrom.equals("")) {

            throw new IOException(dateTime.format(formatter) + file.getName() +"| Некорректно указан банковский счет отправителя в файле ");

        }

        Thread.sleep(100);
        if (checkTo.equals("")) {

            throw new IOException(dateTime.format(formatter) + file.getName()+ "|" +" Некорректно указан банковский счет получателя" );
        }

        Thread.sleep(100);
        if ( Double.parseDouble(amountCheck) < 0 ){

            throw new IOException(dateTime.format(formatter) + file.getName()+ "| Сумма перевода отрицательная");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if ( amountCheck == null){

            throw new IOException(dateTime.format(formatter) +file.getName()+"| Сумма перевода  указана некорректно");
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        boolean containsOnlyNumbers = amountCheck.matches("^[0-9]+$");
        if (!containsOnlyNumbers){

            throw new IOException(dateTime.format(formatter) +file.getName() + "| Сумма перевода в файле указана некорректно");
        }


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if ( Double.parseDouble(amountCheck) == 0){

            throw new IOException(dateTime.format(formatter) + file.getName() +"|Указан нулевой перевод");
        }


        String mas[] = {checkFrom, checkTo, amountCheck,file.getName(), dateTime.format(formatter)};
        // Перемещение файла в архив
        File outputFile = new File(outputDir, file.getName());
        Files.move(filePath, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return mas;
    }
}
