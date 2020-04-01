package task2;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    static String patternStr;
    static String newFileName;
    static BufferedWriter writer;

    public static void readFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String str;
            Pattern pattern = Pattern.compile(patternStr);
            while ((str = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(str);
                while (matcher.find()) {
                    writer.write(matcher.group() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getFiles(String dirName) throws IOException {
        File dir = new File(dirName);
        writer = new BufferedWriter(new FileWriter(newFileName));
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (!item.isDirectory()) {
                    readFile(item.getName());
                }
            }
        } else {
            readFile(dirName);
        }
        writer.close();
    }

    public LogParser(String patternStr, String newFileName) {
        LogParser.patternStr = patternStr;
        LogParser.newFileName = newFileName;
    }

    public static void main(String[] args) throws IOException {
        LogParser parser = new LogParser("Disc \\[applyToPos=0, pos_ID=14, discType=256, ServerDiscType=12", "newFile.txt");
        getFiles("LOGS");
    }
}
