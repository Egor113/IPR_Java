package task1;

import java.io.*;

public class LogSplitter {

    public static int getFileLength(String fileName){
        int fileSize = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String str;
            while ((str = reader.readLine()) != null ) {
                fileSize++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileSize;
    }

    public static void splitFile(String fileName, int fileSize) {
        int newFileSize = fileSize / 10;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String fileNameTemplate = "log";
            if (fileSize % 10 == 0) {
                for (int i = 0; i < 10; i++) {
                    int num = i + 1;
                    String currentFileName = fileNameTemplate + " " + num;
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFileName))){
                        for (int j = 0; j < newFileSize; j++) {
                            writer.write(reader.readLine() + "\n");
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            else {
                for (int i = 0; i < 9; i++) {
                    int num = i + 1;
                    String currentFileName = fileNameTemplate + " " + num;
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFileName))){
                        for (int j = 0; j < newFileSize; j++) {
                            writer.write(reader.readLine() + "\n");
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                String currentFileName = fileNameTemplate + " 10";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFileName))){
                    for (int j = 0; j < newFileSize + fileSize % 10; j++) {
                        writer.write(reader.readLine() + "\n");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        splitFile("main.log.2014-11-17",(getFileLength("main.log.2014-11-17")));
    }

}
