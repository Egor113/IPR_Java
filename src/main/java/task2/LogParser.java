package task2;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    static String patternStr;
    static String newFileName;
    static BufferedWriter writer;

    public LogParser(String patternStr, String newFileName) {
        LogParser.patternStr = patternStr;
        LogParser.newFileName = newFileName;
    }

    public static void findRegExp(String fileName) {
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
                    findRegExp(item.getName());
                }
            }
        } else {
            findRegExp(dirName);
        }
        writer.close();
    }

    public static void splitFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("splitFile.csv"))) {
                String str;
                Pattern patternMessType = Pattern.compile("DEBUG:|TRACE:|INFO :|WARN :");
                Pattern patternAddress = Pattern.compile("[0-9]+[.]+[0-9]+[.]+[0-9]+[.]+[0-9]+[:]+[0-9]+");
                while ((str = reader.readLine()) != null) {
                    Matcher matcherMessType = patternMessType.matcher(str);
                    String bufferBegin = "";
                    String bufferEnd = "";
                    if (matcherMessType.find()) {
                        bufferBegin = str.substring(0, matcherMessType.end());
                        bufferEnd = str.substring(matcherMessType.end(), str.length());
                    }
                    bufferBegin = bufferBegin.replaceAll("\\s+", ";");
                    String address = "", info = "";
                    if ((str.contains("DEBUG:") || str.contains("TRACE:"))){
                        Matcher matcherAddress = patternAddress.matcher(bufferEnd);
                        if (matcherAddress.find()){
                            address = bufferEnd.substring(matcherAddress.start(), matcherAddress.end());
                            info = bufferEnd.substring(matcherAddress.end(), bufferEnd.length());
                        }
                        else {
                            info = bufferEnd;
                        }
                    }
                    else {
                        info = bufferEnd;
                    }
                    if (address.isEmpty()){
                        System.out.println(bufferBegin + " + " + info);
                    } else {
                        System.out.println(bufferBegin + " + " + address + " + " + info);
                    }
//                    System.out.println(bufferBegin);
                    if (matcherMessType.find()) {
                        //System.out.println(str.substring(0, matcherMessType.end()) + " + " + str.substring(matcherMessType.end(), str.length() - 1));
                    }
                    //String dataStr = str.substring(0, m);
                    //System.out.println(dataStr);
                    //writer.write(str.replaceAll("\\s+", ";") + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {
//        LogParser parser = new LogParser("Disc \\[applyToPos=0, pos_ID=14, discType=256, ServerDiscType=12", "newFile.txt");
//        getFiles("LOGS");
        splitFile("log_1_1_1.log");
    }
}
