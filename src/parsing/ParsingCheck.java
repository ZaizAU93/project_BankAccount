package parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ParsingCheck {

    public  Map<String, Double> readAccountCheckFile() {
        Map<String, Double> check = new HashMap<>();
        String fileName = "src/check/check.txt";
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                String accountNumber = parts[0];
                double balance = Double.parseDouble(parts[1]);

                check.put(accountNumber, balance);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + fileName + " не найден");
        }

        return check;
    }
}
