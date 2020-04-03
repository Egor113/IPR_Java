package task2;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    static String patternStr;
    static String newFileNameRegExp;
    static char splitterType;
    static String newFileNameSplit;
    static BufferedWriter writer;

    public static void setPatternStr(String patternStr) {
        LogParser.patternStr = patternStr;
    }

    public static void setNewFileNameRegExp(String newFileNameRegExp) {
        LogParser.newFileNameRegExp = newFileNameRegExp;
    }

    public static void setSplitterType(char splitterType) {
        LogParser.splitterType = splitterType;
    }

    public static void setNewFileNameSplit(String newFileNameSplit) {
        LogParser.newFileNameSplit = newFileNameSplit;
    }

    public static void setWriter(BufferedWriter writer) {
        LogParser.writer = writer;
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

    public static void openFileOrDir(String dirName, OpenFIleMode mode) throws IOException {
        File dir = new File(dirName);
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (!item.isDirectory()) {
                    if (mode == OpenFIleMode.FIND_REG_EXP){
                        writer = new BufferedWriter(new FileWriter(newFileNameRegExp));
                        findRegExp(item.getName());
                        writer.close();
                    } else if (mode == OpenFIleMode.SPLIT_FILE){
                        splitFile(item.getName());
                    }
                }
            }
        } else if (mode == OpenFIleMode.FIND_REG_EXP){
            writer = new BufferedWriter(new FileWriter(newFileNameRegExp));
            findRegExp(dirName);
            writer.close();
        } else if (mode == OpenFIleMode.SPLIT_FILE){
            splitFile(dirName);
        }
    }

    public static void splitFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFileNameSplit))) {
                String str;
                Pattern patternMessType = Pattern.compile("DEBUG:|TRACE:|INFO|WARN");
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
                        info = bufferEnd.substring(2, bufferEnd.length());
                    }
                    writer.write(bufferBegin + splitterType + address + splitterType + info + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        setNewFileNameSplit("splitFileOld.csv");
        setSplitterType(';');
        openFileOrDir("main.log",OpenFIleMode.SPLIT_FILE);
        setNewFileNameRegExp("regExp_new.txt");
        setPatternStr("[0-9]+[.]+[0-9]+[.]+[0-9]+[.]+[0-9]+[:]+[0-9]+");
        openFileOrDir("LOGS", OpenFIleMode.FIND_REG_EXP);
    }
}
