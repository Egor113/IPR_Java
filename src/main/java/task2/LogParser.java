package task2;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    String patternStr;
    String newFileNameRegExp;
    String newFileNameSplit;

    public LogParser(String patternStr, String newFileNameRegExp) {
        this.patternStr = patternStr;
        this.newFileNameRegExp = newFileNameRegExp;
    }

    public LogParser(String newFileNameSplit) {
        this.newFileNameSplit = newFileNameSplit;
    }

    public void findRegExp(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFileNameRegExp))) {
                String str;
                Pattern pattern = Pattern.compile(patternStr);
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

    public void splitFile(String fileName) {
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
                    if ((str.contains("DEBUG:") || str.contains("TRACE:"))) {
                        Matcher matcherAddress = patternAddress.matcher(bufferEnd);
                        if (matcherAddress.find()) {
                            address = bufferEnd.substring(matcherAddress.start(), matcherAddress.end());
                            info = bufferEnd.substring(matcherAddress.end(), bufferEnd.length());
                        } else {
                            info = bufferEnd;
                        }
                    } else {
                        info = bufferEnd.substring(2, bufferEnd.length());
                    }
                    writer.write(bufferBegin + ";" + address + ";" + info + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
