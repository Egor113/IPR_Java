package task2;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {


    public static void readFile(String patternStr, String fileName, String newFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String str;
            Pattern pattern = Pattern.compile(patternStr);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName))) {
                while ((str = reader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(str);
                    while (matcher.find()) {
                        writer.write(matcher.group() + "\n");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readFile("Disc \\[applyToPos=", "log_1.log", "newFile.txt");
    }
}
