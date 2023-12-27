package logics;
import parsing.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public class ReportFile {
    private ArrayList<String> report = new ArrayList<>();

    public void setReport(ArrayList<String> report) {
        report = this.report;
    }

    public ArrayList<String> glueReportParsingsTransfer(Parsing parsing, SearchCheck searchCheck){
        report = parsing.getReportParsing();
        report.addAll(searchCheck.getReadyMoneyTtransfer());
        return report;
    }

    public void writeReportFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter( "src/report/report.txt"))) {
            for (String element : report) {
                writer.write(element);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }


